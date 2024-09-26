package com.sc.backend;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.FixedRateTask;

import java.text.DateFormat;
import java.util.Date;

@EnableScheduling
@SpringBootTest
class BackendApplicationTests {

    @Test
    @Scheduled(fixedRate = 1000)
    void timerFunction() {
        System.out.println(DateFormat.getDateInstance().format(new Date()) + "test");
    }

}
