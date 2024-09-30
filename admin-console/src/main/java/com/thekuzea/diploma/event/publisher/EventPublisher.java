package com.thekuzea.diploma.event.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Slf4j
@Component
public class EventPublisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void sendEvent(final DomainEvent event) {
        log.debug("About to send event with class name: {}", event.getClass());
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
