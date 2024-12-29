package com.sphere.demo.scheduler;

import com.sphere.demo.service.project.ProjectCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectScheduler {

    private final ProjectCommandService projectCommandService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void closeExpiredProjects() {
        projectCommandService.closeExpiredProjects();
    }
}
