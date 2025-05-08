package com.ex.route_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupLogger implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupLogger.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Приложение готово к работе");
        logger.info("Приложение готово к работе");
        logger.info("Приложение готово к работе");

    }
}