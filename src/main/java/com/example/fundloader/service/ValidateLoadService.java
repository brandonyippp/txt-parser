package com.example.fundloader.service;

import com.example.fundloader.model.TodayData;
import com.example.fundloader.model.Load;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.example.fundloader.constants.ApplicationConstants.*;

@Service
public class ValidateLoadService {
    private final Map<String, Set<String>> processedLoadIds;

    public ValidateLoadService() {
        this.processedLoadIds = new HashMap<>();
    }

    public Boolean isWeeklyReset(Instant previousTransactionDate, Instant currentTransactionDate) {
        DayOfWeek dayOfPreviousTransactionDate = getDayOfWeek(previousTransactionDate);
        DayOfWeek dayOfCurrentTransactionDate = getDayOfWeek(currentTransactionDate);

        return dayOfPreviousTransactionDate == DayOfWeek.SUNDAY && dayOfCurrentTransactionDate == DayOfWeek.MONDAY;
    }

    // Assumes dates are received in ascending order
    public Boolean isDailyReset(Instant previousTransactionDate, Instant currentTransactionDate) {
        DayOfWeek dayOfPreviousTransactionDate = getDayOfWeek(previousTransactionDate);
        DayOfWeek dayOfCurrentTransactionDate = getDayOfWeek(currentTransactionDate);

        return dayOfPreviousTransactionDate != dayOfCurrentTransactionDate;
    }

    public Boolean isValidLoad(Load load, TodayData customerTodayData, BigDecimal weeklyLoadAmount) {
        BigDecimal todayTotalLoaded = customerTodayData.getDailyLoadAmount();
        BigDecimal loadAmount = load.getLoadAmount();
        int todayLoadCount = customerTodayData.getDailyLoadCount();
        
        /* Disregard case of duplicate load_id for a given user_id */
        if (hasAlreadyBeenProcessedByUser(load.getCustomerId(), load.getLoadId())) {
            return null;
        }

        return !isMaxDailyLoadCount(todayLoadCount)
                && !isMaxDailyLoadAmount(loadAmount, todayTotalLoaded)
                && !isMaxWeeklyLoadAmount(loadAmount, weeklyLoadAmount);
    }

    /* Helper Functions */

    private DayOfWeek getDayOfWeek(Instant date) {
        return date.atZone(ZoneId.of("UTC")).getDayOfWeek();
    }

    private Boolean hasAlreadyBeenProcessedByUser(String user_id, String load_id) {
        if (!processedLoadIds.containsKey(user_id)) {
            processedLoadIds.put(user_id, new HashSet<String>());
        }

        if (!processedLoadIds.get(user_id).contains(load_id)) {
            processedLoadIds.get(user_id).add(load_id);

            return false;
        }

        return true;
    }

    private Boolean isMaxDailyLoadCount(Integer dailyLoadCount) {
        return dailyLoadCount.equals(MAX_DAILY_LOAD_COUNT);
    }

    private Boolean isMaxDailyLoadAmount(BigDecimal loadAmount, BigDecimal dailyLoadAmount) {

        return loadAmount.add(dailyLoadAmount).compareTo(MAX_DAILY_LOAD_AMOUNT) > 0;
    }

    private Boolean isMaxWeeklyLoadAmount(BigDecimal loadAmount, BigDecimal weeklyLoadAmount) {
        return loadAmount.add(weeklyLoadAmount).compareTo(MAX_WEEKLY_LOAD_AMOUNT) > 0;
    }
}
