/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.services.executor;

import com.gaurav.rest.lambda.beans.ExecutorTask;
import org.apache.commons.exec.CommandLine;

public interface IExecutorCommands {

    CommandLine getCommandLine(ExecutorTask executorTask);
}
