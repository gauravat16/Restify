package com.gaurav.rest.lambda.controllers;

import com.gaurav.rest.lambda.beans.ExecutorTaskOutput;
import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
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

    @Autowired
    private RestConfigurationManager restConfigurationManager;

    @Autowired
    private DatabaseService databaseService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessController.class);


    @RequestMapping(value = "/lambda/get/execute/{scriptName}")
    public Response executeScripts(@PathVariable String scriptName) {
        Response response;

        logger.info("Current Rest Call for Script ::: " + scriptName);

        RestJob restJob = restConfigurationManager.getRestJob(scriptName);

        logger.info("Rest Job Found " + restJob);

        if (null == restJob) {
            response = new Response(String.valueOf(ErrorCodes.ERROR_TERMINATE));
            response.setOutput("No Rest Job found for the alias " + scriptName);
            return response;
        }

        ExecutorTaskOutput taskOutput;

        ExecutorTask executorTask = buildTaskbyRestJob(restJob);


        try {

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


        RestJobPostBean restJobPostBean = new RestJobPostBean(response);
        restJobPostBean.setRestJob(restJob);

        databaseService.addJob(restJobPostBean);


        return response;
    }


    @PostMapping(value = "/lambda/post/execute")
    public Response executeScripts(@RequestBody RestJob restJob) {

        logger.info("Rest Job Recieved " + restJob);
        restConfigurationManager.addRestJob(restJob);

        ExecutorTaskOutput taskOutput;
        Response response;
        ExecutorTask executorTask = buildTaskbyRestJob(restJob);


        try {

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


        RestJobPostBean restJobPostBean = new RestJobPostBean(response);
        restJobPostBean.setRestJob(restJob);

        databaseService.addJob(restJobPostBean);

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
