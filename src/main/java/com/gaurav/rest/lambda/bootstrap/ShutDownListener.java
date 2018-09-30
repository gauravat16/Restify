/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.bootstrap;


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
        logger.debug("Application forced to close!");
    }

}
