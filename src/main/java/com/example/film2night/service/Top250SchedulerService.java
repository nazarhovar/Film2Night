package com.example.film2night.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Top250SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(Top250SchedulerService.class);

    private final Top250LoadService top250LoadService;

    @Value("${top250.scheduler.enabled:true}")
    private boolean schedulerEnabled;

    public Top250SchedulerService(Top250LoadService top250LoadService) {
        this.top250LoadService = top250LoadService;
    }

    @PostConstruct
    public void init() {
        if (schedulerEnabled) {
            log.info("Top250 scheduler enabled, running initial load...");
            try {
                top250LoadService.loadTop250();
            } catch (Exception e) {
                log.error("Initial Top250 load failed: {}", e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 86400000)
    public void runDailySync() {
        if (!schedulerEnabled) return;
        log.info("Running scheduled Top250 sync...");
        try {
            top250LoadService.loadTop250();
        } catch (Exception e) {
            log.error("Scheduled Top250 sync failed: {}", e.getMessage());
        }
    }
}
