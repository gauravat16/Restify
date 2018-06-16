package com.gaurav.restify.services;

import com.gaurav.restify.beans.ExecutorTask;
import com.gaurav.restify.beans.ExecutorTaskOutput;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ExecutorService {

    public ExecutorTaskOutput executeTask(ExecutorTask executorTask) throws IOException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();

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
        CommandLine commandLine = new CommandLine(executorTask.getCommandType().toString());
        commandLine.addArgument(executorTask.getCommand());
        if (null != executorTask.getArgs()) {
            for (String arg : executorTask.getArgs()) {

                commandLine.addArgument(arg);

            }

        }

        return commandLine;
    }
}
