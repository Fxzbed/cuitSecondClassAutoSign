package com.sc.backend.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class TimeStampUtil {
    public long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public Boolean timeValidityChecker(String begin, String end) {
        long currentTimeStamp = getCurrentTimeStamp();
        return (currentTimeStamp > timeStampTrans(begin)) && currentTimeStamp < timeStampTrans(end);
    }

    public long timeStampTrans(String timeStr) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        return parsedDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public String getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(currentDate);
    }
}
