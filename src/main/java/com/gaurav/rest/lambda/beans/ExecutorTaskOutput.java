/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.beans;

public class ExecutorTaskOutput {

    private int exitCode;
    private String output;
    private String errorOut;

    public String getErrorOut() {
        return errorOut;
    }

    public void setErrorOut(String errorOut) {
        this.errorOut = errorOut;
    }

    public ExecutorTaskOutput(int exitCode) {
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
