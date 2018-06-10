package com.gaurav.restify.controllers;

import com.gaurav.restify.beans.ExecutorTask;
import com.gaurav.restify.beans.ExecutorTaskOutput;
import com.gaurav.restify.beans.Response;
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


    @RequestMapping(value = "/execute/{scriptName}")
    public Response executeScripts(@PathVariable String scriptName) {

        ExecutorTaskOutput taskOutput = null;
        try {


            ExecutorTask executorTask = new ExecutorTask.ExecutorTaskBuilder("/Users/gaurav/Downloads", scriptName, ExecutorConstants.BASH)
                    .setArgs(new String[]{})
                    .setWaitTime(10)
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
