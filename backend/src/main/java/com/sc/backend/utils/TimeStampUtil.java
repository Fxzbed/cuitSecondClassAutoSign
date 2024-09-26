package com.sc.backend.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeStampUtil {
    public String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public Boolean timeValidityChecker(String timeString) {
        return  true;
    }

    public String getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(currentDate);
    }
}
