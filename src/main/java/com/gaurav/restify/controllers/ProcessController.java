package com.gaurav.restify.controllers;

import com.gaurav.restify.beans.ExecutorTask;
import com.gaurav.restify.beans.ExecutorTaskOutput;
import com.gaurav.restify.beans.Response;
import com.gaurav.restify.configuration.RestConfigurationManager;
import com.gaurav.restify.configuration.configurationBeans.RestJob;
import com.gaurav.restify.constants.ErrorCodes;
import com.gaurav.restify.constants.ExecutorConstants;
import com.gaurav.restify.services.ExecutorService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProcessController {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RestConfigurationManager restConfigurationManager;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessController.class);


    @RequestMapping(value = "/execute/{scriptName}")
    public Response executeScripts(@PathVariable String scriptName) {

        logger.info("Current Rest Call for Script ::: " + scriptName);

        RestJob restJob = restConfigurationManager.getRestJob(scriptName);

        logger.info("Rest Job Found " + restJob);


        ExecutorTaskOutput taskOutput;
        Response response;
        try {


            ExecutorTask executorTask = new ExecutorTask.ExecutorTaskBuilder(restJob.getPath(), restJob.getCommand(), ExecutorConstants.valueOf(restJob.getCommandType()))
                    .setArgs(restJob.getArgs())
                    .setWaitTime(restJob.getWaitTime())
                    .build();


            taskOutput = executorService.executeTask(executorTask);
            response = new Response(String.valueOf(taskOutput.getExitCode()));
            response.setOutput(taskOutput.getOutput());


        } catch (IOException ioEx) {

            logger.error("File Not Found", ioEx);
            response = new Response(String.valueOf(ErrorCodes.ERROR_TERMINATE));
            response.setOutput(ioEx.toString());

        } catch (Exception e) {

            logger.error("Script execution failed! ", e);
            response = new Response(String.valueOf(ErrorCodes.ERROR_TERMINATE));
            response.setOutput(e.toString());

        }


        return response;
    }


}
