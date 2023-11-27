package com.example.fundloader.model;

import java.math.BigDecimal;
import java.time.Instant;

public class TodayData {
    private int dailyLoadCount;
    private BigDecimal dailyLoadAmount;
    private String transactionDate;

    public TodayData(Integer dailyLoadCount, BigDecimal loadAmount, String transactionDate) {
        this.dailyLoadCount = dailyLoadCount;
        this.dailyLoadAmount = loadAmount;
        this.transactionDate = transactionDate;
    }

    /* Getters and Setters */

    public int getDailyLoadCount() {
        return this.dailyLoadCount;
    }

    public void setDailyLoadCount(int dailyLoadCount) {
        this.dailyLoadCount = dailyLoadCount;
    }

    public BigDecimal getDailyLoadAmount() {
        return this.dailyLoadAmount;
    }

    public void setDailyLoadAmount(BigDecimal dailyLoadAmount) {
        this.dailyLoadAmount = dailyLoadAmount;
    }

    public String getTransactionDateAsString() {
        return transactionDate;
    }

    public Instant getTransactionDateAsInstant() {
        return Instant.parse(this.transactionDate);
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
