/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.beans;


public class Response {

    private String processExecCode;

    private String output;

    private String errors;

    public Response(String processExecCode) {
        this.processExecCode = processExecCode;
    }

    public String getProcessExecCode() {
        return processExecCode;
    }

    public void setProcessExecCode(String processExecCode) {
        this.processExecCode = processExecCode;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Response{" +
                "processExecCode='" + processExecCode + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
