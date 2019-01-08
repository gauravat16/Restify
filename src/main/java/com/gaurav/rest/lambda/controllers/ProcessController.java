/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.controllers;

import com.gaurav.rest.lambda.beans.ExecutorTaskOutput;
import com.gaurav.rest.lambda.configuration.RestConfigurationManager;
import com.gaurav.rest.lambda.constants.ErrorCodes;
import com.gaurav.rest.lambda.constants.ExecutorConstants;
import com.gaurav.rest.lambda.beans.ExecutorTask;
import com.gaurav.rest.lambda.beans.Response;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestJob;
import com.gaurav.rest.lambda.services.database.DatabaseService;
import com.gaurav.rest.lambda.services.executor.ExecutorService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ProcessController {

    @Autowired
    private ExecutorService executorService;

    private RestConfigurationManager restConfigurationManager = RestConfigurationManager.getInstance();

    @Autowired
    private DatabaseService databaseService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessController.class);


    @RequestMapping(value = "/lambda/get/execute/{scriptName}")
    public Response executeScripts(@PathVariable String scriptName) throws IOException {
        Response response;

        RestJob restJob = restConfigurationManager.getRestJob(scriptName);

        logger.info("Rest Job Found " + restJob);

        if (null == restJob) {
            response = new Response(String.valueOf(ErrorCodes.ERROR_TERMINATE));
            response.setOutput("No Rest Job found for the alias " + scriptName);
            return response;
        }

        ExecutorTaskOutput taskOutput;

        ExecutorTask executorTask = buildTaskbyRestJob(restJob);


        taskOutput = executorService.executeTask(executorTask);
        response = new Response(String.valueOf(taskOutput.getExitCode()));
        response.setOutput(taskOutput.getOutput());


        return response;
    }


    @PostMapping(value = "/lambda/post/execute")
    public Response executeScripts(@RequestBody RestJob restJob) throws IOException {

        restConfigurationManager.addRestJob(restJob);

        ExecutorTaskOutput taskOutput;
        Response response;
        ExecutorTask executorTask = buildTaskbyRestJob(restJob);


        taskOutput = executorService.executeTask(executorTask);
        response = new Response(String.valueOf(taskOutput.getExitCode()));
        response.setOutput(taskOutput.getOutput());


        return response;

    }


    private ExecutorTask buildTaskbyRestJob(RestJob restJob) {

        ExecutorConstants commandType = null;
        ExecutorTask executorTask = null;
        try {
            commandType = ExecutorConstants.valueOf(restJob.getCommandType());


        } catch (IllegalArgumentException e) {
            logger.error("ExecutorConstants not present for job's command type");
        }


        executorTask = new ExecutorTask.ExecutorTaskBuilder(restJob.getPath(), restJob.getCommand(), commandType, restJob.getAlias())
                .setArgsCommand(restJob.getArgsForCommand())
                .setArgsCommandType(restJob.getArgsForCommandType())
                .setWaitTime(restJob.getWaitTime())
                .build();


        return executorTask;


    }


}
