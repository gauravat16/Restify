/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.services.executor;

import com.gaurav.rest.lambda.beans.ExecutorTask;
import com.gaurav.rest.lambda.beans.ExecutorTaskOutput;
import com.gaurav.rest.lambda.configuration.RestConfigurationManager;
import org.apache.commons.exec.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ExecutorService {

    private RestConfigurationManager restConfigurationManager = RestConfigurationManager.getInstance();

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExecutorService.class);


    public ExecutorTaskOutput executeTask(ExecutorTask executorTask) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream, errorStream);

        CommandLine cmdLine = getCommandLine(executorTask);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(executorTask.getPath()));
        executor.setStreamHandler(pumpStreamHandler);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(executorTask.getWaitTime() * 1000);
        executor.setWatchdog(watchdog);

        int exitValue = executor.execute(cmdLine);

        ExecutorTaskOutput taskOutput = new ExecutorTaskOutput(exitValue);

        taskOutput.setOutput(outputStream.toString());
        taskOutput.setErrorOut(errorStream.toString());


        return taskOutput;

    }

    public CommandLine getCommandLine(ExecutorTask executorTask) {

        String commandType;
        if (null == executorTask.getCommandType()) {
            commandType = restConfigurationManager.getRestJob(executorTask.getAlias()).getCommandType();
        } else {
            commandType = executorTask.getCommandType().toString();
        }

        CommandLine commandLine = new CommandLine(commandType);

        if (executorTask.getArgsCommandType() != null)
            Arrays.stream(executorTask.getArgsCommandType()).forEach(commandLine::addArgument);

        commandLine.addArgument(executorTask.getCommand());

        if (executorTask.getArgsCommand() != null)
            Arrays.stream(executorTask.getArgsCommand()).forEach(commandLine::addArgument);

        logger.info("Command Line :: " + commandLine.toString());

        return commandLine;
    }
}
