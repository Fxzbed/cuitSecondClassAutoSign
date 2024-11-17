package com.sc.backend;

import com.sc.backend.service.cuit.AutoSignService;
import com.sc.backend.utils.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class BackendApplication {

    @Autowired
    private AutoSignService autoSignService;
    @Autowired
    private TimeStampUtil timeStampUtil;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void taskPoll() {
        autoSignService.taskPolling();
    }

    @Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)
    public void taskDelete() {
        //...
    }

}
