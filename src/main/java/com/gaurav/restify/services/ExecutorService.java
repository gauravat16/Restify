package com.gaurav.restify.services;

import com.gaurav.restify.beans.ExecutorTask;
import com.gaurav.restify.beans.ExecutorTaskOutput;
import com.gaurav.restify.configuration.RestConfigurationManager;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ExecutorService {

    @Autowired
    private RestConfigurationManager restConfigurationManager;

    public ExecutorTaskOutput executeTask(ExecutorTask executorTask) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(byteOutputStream);
        CommandLine cmdLine = getCommandLine(executorTask);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(executorTask.getPath()));
        executor.setStreamHandler(pumpStreamHandler);

        int exitValue = executor.execute(cmdLine);

        ExecutorTaskOutput taskOutput = new ExecutorTaskOutput(exitValue);

        taskOutput.setOutput(byteOutputStream.toString());

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
        commandLine.addArgument(executorTask.getCommand());
        if (null != executorTask.getArgs()) {
            for (String arg : executorTask.getArgs()) {

                commandLine.addArgument(arg);

            }

        }

        return commandLine;
    }
}
