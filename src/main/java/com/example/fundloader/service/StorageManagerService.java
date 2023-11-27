package com.example.fundloader.service;

import com.example.fundloader.model.Load;
import com.example.fundloader.model.TodayData;
import com.example.fundloader.repository.LoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class StorageManagerService {
    private final CacheInteractService cacheInteractService;
    private final ValidateLoadService validateLoadService;
    private final LoadRepository loadRepository;
    private Instant previousLineTime;

    @Autowired
    public StorageManagerService(CacheInteractService cacheInteractService, ValidateLoadService validateLoadService, LoadRepository loadRepository) {
        this.cacheInteractService = cacheInteractService;
        this.validateLoadService = validateLoadService;
        this.loadRepository = loadRepository;
    }

    public Boolean processLoad(Load load) {
        if (previousLineTime != null) {
            assessDailyAndWeeklyCalendarReset(previousLineTime, load.getTransactionDateAsInstant());
        }

        //Read value in from cache if available
        TodayData customerTodayData = cacheInteractService.loadCustomerTodayData(load);
        BigDecimal weeklyLoadAmount = cacheInteractService.loadCustomerWeeklyData(load);

        Boolean isValidInput = validateLoadService.isValidLoad(load, customerTodayData, weeklyLoadAmount);
        boolean isDuplicateLoadIdForUser = isValidInput == null;

        if (isDuplicateLoadIdForUser) {
            return null;
        } else if (!isValidInput) {
            return false;
        }

        try {
            //POST to H2 Database
            loadRepository.save(load);

            //Update Cache
            BigDecimal weekTotalSum = weeklyLoadAmount.add(load.getLoadAmount());
            previousLineTime = cacheInteractService.updateCustomerTodayRecord(customerTodayData, load).getTransactionDateAsInstant();
            cacheInteractService.updateCustomerWeeklyRecord(weekTotalSum, load);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

    private void assessDailyAndWeeklyCalendarReset(Instant previousTransactionDate, Instant currentTransactionDate) {
        if (validateLoadService.isWeeklyReset(previousTransactionDate, currentTransactionDate)) {
            cacheInteractService.clearDailyCache();
            cacheInteractService.clearWeeklyCache();
        } else if (validateLoadService.isDailyReset(previousTransactionDate, currentTransactionDate)) {
            cacheInteractService.clearDailyCache();
        }
    }
}
