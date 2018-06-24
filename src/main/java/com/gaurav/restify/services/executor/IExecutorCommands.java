package com.gaurav.restify.services.executor;

import com.gaurav.restify.beans.ExecutorTask;
import org.apache.commons.exec.CommandLine;

public interface IExecutorCommands {

    CommandLine getCommandLine(ExecutorTask executorTask);
}
