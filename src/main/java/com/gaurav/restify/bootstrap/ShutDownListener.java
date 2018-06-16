package com.gaurav.restify.bootstrap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ShutDownListener {

    Logger logger = LoggerFactory.getLogger(ShutDownListener.class);

    @EventListener(ContextClosedEvent.class)
    private void applicationStopped() {
        logger.warn("Application forced to close!");
    }

}
