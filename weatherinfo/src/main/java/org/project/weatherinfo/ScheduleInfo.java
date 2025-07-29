package org.project.weatherinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduleInfo {

    @Async
    @Scheduled(cron="*/5 * * * * *")
    public void schedule() {
        log.info("Scheduler Working");
    }
}
