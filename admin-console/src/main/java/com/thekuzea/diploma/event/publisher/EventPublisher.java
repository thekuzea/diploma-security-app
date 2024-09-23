package com.thekuzea.diploma.event.publisher;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Component
public class EventPublisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void sendEvent(final DomainEvent event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
