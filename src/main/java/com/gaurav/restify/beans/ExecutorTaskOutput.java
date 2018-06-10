package com.gaurav.restify.beans;

import java.util.List;

public class ExecutorTaskOutput {

    private int exitCode;
    private String output;

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
