/*
 * Copyright (c) 2019.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.aspect;

import com.gaurav.rest.lambda.beans.Response;
import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.rest.lambda.configuration.RestConfigurationManager;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestJob;
import com.gaurav.rest.lambda.constants.AspectConstants;
import com.gaurav.rest.lambda.controllers.ProcessController;
import com.gaurav.rest.lambda.services.database.DatabaseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ControllerAspect {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    private RestConfigurationManager restConfigurationManager = RestConfigurationManager.getInstance();


    @Autowired
    private DatabaseService databaseService;


    /**
     * This function logs the rest job/alias.
     *
     * @param joinPoint
     */
    @Before("execution( * com.gaurav.rest.lambda.controllers.*.executeScripts(..))")
    public void logRestJob(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        switch (getJoinPointType(joinPoint)) {
            case AspectConstants.EXECUTE_GET:
                logger.info("Current Rest Call for Script ::: " + args[0]);
                break;

            case AspectConstants.EXECUTE_POST:
                logger.info("Current Rest Call for Script ::: " + args[0]);
                break;
            case AspectConstants.ERROR:
                break;

        }

    }

    /**
     * This method will post Response to Database
     *
     * @param joinPoint
     * @param response
     */

    @AfterReturning(value = "execution( * com.gaurav.rest.lambda.controllers.*.executeScripts(..))", returning = "response")
    public void postRestJobToDB(JoinPoint joinPoint, Object response) {
        Object[] args = joinPoint.getArgs();

        RestJobPostBean restJobPostBean = new RestJobPostBean((Response) response);

        RestJob restJob = null;

        switch (getJoinPointType(joinPoint)) {
            case AspectConstants.EXECUTE_GET:
                restConfigurationManager.getRestJob((String) args[0]);
                break;

            case AspectConstants.EXECUTE_POST:
                restJob = (RestJob) args[0];
                break;
            case AspectConstants.ERROR:
                break;
        }

        if (restJob != null) {
            logger.info("Response for  " + restJob.getAlias() + "=====>" + response);
            restJobPostBean.setRestJob(restJob);
            databaseService.addJob(restJobPostBean);
        }


    }

    /**
     * This method accepts a {@link JoinPoint} and on the basis of the args it can return
     * {@link AspectConstants#EXECUTE_GET} for {@link ProcessController#executeScripts(String)}
     * {@link AspectConstants#EXECUTE_POST} for {@link ProcessController#executeScripts(RestJob)}
     * {@link AspectConstants#ERROR} if no match is found.
     *
     * @param joinPoint
     * @return
     */
    private String getJoinPointType(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {

            /**
             * If the length is 1 then this is can be a post/get method.
             */
            if (args.length == 1) {
                Object arg = args[0];

                if (arg instanceof RestJob)
                    return AspectConstants.EXECUTE_POST;
                if (arg instanceof String)
                    return AspectConstants.EXECUTE_GET;

            }

        }

        return AspectConstants.ERROR;
    }


}
