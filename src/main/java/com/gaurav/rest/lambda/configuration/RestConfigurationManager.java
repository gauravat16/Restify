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


    private static RestConfigurationManager restConfigurationManager = null;
    private static RestConfiguration restConfiguration = null;
    private static HashMap<String, RestJob> restJobHashMap = null;
    private static final Logger logger = LoggerFactory.getLogger(RestConfigurationManager.class);


    private RestConfigurationManager() {

    }


    public static RestConfigurationManager getInstance() {
        logger.info("RestConfigurationManager :: Instantiate");

        if (null == restConfigurationManager) {
            try {
                restConfiguration = (RestConfiguration) ConfigurationUtil.XMLtoObject(RestConfiguration.class, ConfigurationConstants.REST_CONFIG_FILE);
                instantiateRestJobMap();
                return new RestConfigurationManager();

            } catch (JAXBException e) {

                logger.error("Could not parse xml file - " + ConfigurationConstants.REST_CONFIG_FILE, e);

            } catch (FileNotFoundException e) {
                logger.error("xml file not found- " + ConfigurationConstants.REST_CONFIG_FILE, e);

            }


        }

        return restConfigurationManager;
    }


    private static void instantiateRestJobMap() {
        logger.info("Going to instantiate rest job map");
        if (null != restConfiguration) {
            restJobHashMap = new HashMap<>();
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

        restConfigurationManager = null;

        RestConfigurationManager.getInstance();


    }


}
