package com.gaurav.restify.bootstrap;

import com.gaurav.restify.configuration.ConfigurationUtil;
import com.gaurav.restify.configuration.RestConfigurationManager;
import com.gaurav.restify.constants.ConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;


@Component
public class ConfigurationListener {

    Logger logger = LoggerFactory.getLogger(ConfigurationUtil.class);


    @EventListener(ContextRefreshedEvent.class)
    private void instantiateConfiguration() {
        logger.info("Startup Listener :: Configuration");
        RestConfigurationManager.getInstance();
    }

    @EventListener(ContextRefreshedEvent.class)
    private void createDirectories() {
        logger.info("Startup Listener :: Directories");

        try {
            Files.createDirectories(ConfigurationConstants.REST_LOG_PATH);
            Files.createDirectories(ConfigurationConstants.REST_CONFIG_PATH);
        } catch (IOException e) {
            logger.error("Could not create directories", e);
            System.exit(0);

        }
    }
}
