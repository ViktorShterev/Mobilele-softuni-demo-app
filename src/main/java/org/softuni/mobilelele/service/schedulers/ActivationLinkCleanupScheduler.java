package org.softuni.mobilelele.service.schedulers;

import org.softuni.mobilelele.service.UserActivationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActivationLinkCleanupScheduler {

    private  final UserActivationService userActivationService;

    public ActivationLinkCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    //    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void cleanUp() {
        //System.out.println("Cleaning up activation links " + LocalDateTime.now());
        //TODO
        this.userActivationService.cleanupObsoleteActivationLinks();
    }
}
