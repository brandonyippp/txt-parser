package com.example.fundloader.service;

import com.example.fundloader.model.TodayData;
import com.example.fundloader.model.Load;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.fundloader.constants.ApplicationConstants.DAILY_LOADS_CACHE;
import static com.example.fundloader.constants.ApplicationConstants.WEEKLY_LOADS_CACHE;

@Service
public class CacheInteractService {

    @Cacheable(value = DAILY_LOADS_CACHE, key = "#load.customerId")
    public TodayData loadCustomerTodayData(Load load) {
        return new TodayData(0, new BigDecimal(0), null);
    }

    @Cacheable(value = WEEKLY_LOADS_CACHE, key = "#load.customerId")
    public BigDecimal loadCustomerWeeklyData(Load load) {
        return new BigDecimal(0);
    }

    @CachePut(value = DAILY_LOADS_CACHE, key = "#load.customerId")
    public TodayData updateCustomerTodayRecord(TodayData customerTodayData, Load load) {
        BigDecimal todayTotalLoaded = customerTodayData.getDailyLoadAmount();
        int todayLoadCount = customerTodayData.getDailyLoadCount();

        customerTodayData.setTransactionDate(load.getTransactionDateAsString());
        customerTodayData.setDailyLoadAmount(todayTotalLoaded.add(load.getLoadAmount()));
        customerTodayData.setDailyLoadCount(todayLoadCount + 1);

        return customerTodayData;
    }

    @CachePut(value = WEEKLY_LOADS_CACHE, key = "#load.customerId")
    public BigDecimal updateCustomerWeeklyRecord(BigDecimal weekTotalSum, Load load) {
        return weekTotalSum;
    }

    @CacheEvict(value = DAILY_LOADS_CACHE, allEntries = true)
    public void clearDailyCache() {
    }

    @CacheEvict(value = WEEKLY_LOADS_CACHE, allEntries = true)
    public void clearWeeklyCache() {
    }
}
