package com.nike.wingtips.springboot2.webflux;

import com.nike.wingtips.util.asynchelperwrapper.ScheduledExecutorServiceWithTracing;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import reactor.core.scheduler.Schedulers;

/**
 * Spring {@link ApplicationListener} responsible for initializing reactors scheduler hook
 * with wingtips support at a Spring Boot Application Startup
 * @author Biju Kunjummen
 * @author Rafaela Breed
 */

class WingtipsReactorInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private static final String WINGTIPS_SCHEDULER_KEY = "WINGTIPS_REACTOR";

    /**
     * Register a {@link reactor.core.scheduler.Scheduler} hook when
     * a Spring Boot application is ready,
     * that ensures that when a thread is borrowed, tracing details
     * are propagated to the thread
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Schedulers.addExecutorServiceDecorator(
                WINGTIPS_SCHEDULER_KEY,
                (scheduler, schedulerService) -> new ScheduledExecutorServiceWithTracing(schedulerService));
    }
}