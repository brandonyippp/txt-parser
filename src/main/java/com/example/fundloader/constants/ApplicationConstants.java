package com.example.fundloader.constants;

import java.math.BigDecimal;

public class ApplicationConstants {
    /* Load Constraints */
    public static final Integer MAX_DAILY_LOAD_COUNT = 3;
    public static final BigDecimal MAX_DAILY_LOAD_AMOUNT = BigDecimal.valueOf(5000);
    public static final BigDecimal MAX_WEEKLY_LOAD_AMOUNT = BigDecimal.valueOf(20000);

    /* Cache Names */
    public static final String DAILY_LOADS_CACHE = "daily-loads";
    public static final String WEEKLY_LOADS_CACHE = "weekly-loads";
}
