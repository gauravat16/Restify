package com.gaurav.restify.controllers;

import com.gaurav.restify.beans.ExecutorTask;
import com.gaurav.restify.beans.ExecutorTaskOutput;
import com.gaurav.restify.beans.Response;
import com.gaurav.restify.configuration.RestConfigurationManager;
import com.gaurav.restify.configuration.configurationBeans.RestJob;
import com.gaurav.restify.constants.ExecutorConstants;
import com.gaurav.restify.services.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@RestController
public class ProcessController {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RestConfigurationManager restConfigurationManager;


    @RequestMapping(value = "/execute/{scriptName}")
    public Response executeScripts(@PathVariable String scriptName) {

        RestJob restJob = restConfigurationManager.getRestJob(scriptName);


        ExecutorTaskOutput taskOutput = null;
        try {


            ExecutorTask executorTask = new ExecutorTask.ExecutorTaskBuilder(restJob.getPath(), restJob.getCommand(), ExecutorConstants.valueOf(restJob.getCommandType()))
                    .setArgs(restJob.getArgs())
                    .setWaitTime(restJob.getWaitTime())
                    .build();


            taskOutput = executorService.executeTask(executorTask);


        } catch (IOException ioEx) {

            ioEx.printStackTrace();
        }


        Response response = new Response(String.valueOf(taskOutput.getExitCode()));
        response.setOutput(taskOutput.getOutput());

        return response;
    }


}
