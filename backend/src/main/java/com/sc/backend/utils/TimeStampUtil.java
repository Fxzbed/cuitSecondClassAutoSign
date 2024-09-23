package com.sc.backend.utils;

import org.springframework.stereotype.Component;

@Component
public class TimeStampUtil {
    public String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public Boolean timeValidityChecker(String timeString) {
        return  true;
    }
}
