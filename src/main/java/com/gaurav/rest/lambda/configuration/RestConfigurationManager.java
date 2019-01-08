/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.configuration;


import com.gaurav.rest.lambda.constants.ConfigurationConstants;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestConfiguration;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Component
public class RestConfigurationManager {


    private static RestConfiguration restConfiguration = null;
    private static HashMap<String, RestJob> restJobHashMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(RestConfigurationManager.class);

    private RestConfigurationManager() {
        logger.info("RestConfigurationManager :: Instantiate");
        setUpXMLConfig();
    }


    private void setUpXMLConfig() {
        try {
            restConfiguration = (RestConfiguration) ConfigurationUtil.XMLtoObject(RestConfiguration.class, ConfigurationConstants.REST_CONFIG_FILE);
            instantiateRestJobMap();
        } catch (JAXBException e) {

            logger.error("Could not parse xml file - " + ConfigurationConstants.REST_CONFIG_FILE, e);

        } catch (FileNotFoundException e) {
            logger.error("xml file not found- " + ConfigurationConstants.REST_CONFIG_FILE);
        }
    }

    private static class RestConfigurationManagerHelper {
        private static final RestConfigurationManager INSTANCE = new RestConfigurationManager();
    }


    public static RestConfigurationManager getInstance() {
        return RestConfigurationManagerHelper.INSTANCE;
    }


    private static void instantiateRestJobMap() {
        logger.info("Going to instantiate rest job map");
        if (null != restConfiguration) {
            restConfiguration.getJobs().forEach(job -> restJobHashMap.put(job.getAlias(), job));
        } else {
            logger.debug("restConfiguration is null");

        }
    }

    public RestJob getRestJob(String commandName) {
        return restJobHashMap.get(commandName);
    }

    public void addRestJob(RestJob restJob) {

        restJobHashMap.put(restJob.getAlias(), restJob);
    }


    public void refreshConfiguration() {
        logger.info("Going to refresh rest job map");
        setUpXMLConfig();

    }
}
