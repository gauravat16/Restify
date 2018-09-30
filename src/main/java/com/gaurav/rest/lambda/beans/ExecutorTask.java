/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.beans;

import com.gaurav.rest.lambda.constants.ExecutorConstants;


public class ExecutorTask {
    private String path;
    private ExecutorConstants commandType;
    private String command;
    private String alias;
    private String[] argsCommand;
    private String[] argsCommandType;
    private long waitTime;

    private ExecutorTask(ExecutorTaskBuilder executorTaskBuilder) {

        this.path = executorTaskBuilder.path;
        this.command = executorTaskBuilder.command;
        this.argsCommand = executorTaskBuilder.argsCommand;
        this.argsCommandType = executorTaskBuilder.argsCommandType;
        this.waitTime = executorTaskBuilder.waitTime;
        this.commandType = executorTaskBuilder.commandType;
        this.alias = executorTaskBuilder.alias;
    }


    public String getPath() {
        return path;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgsCommand() {
        return argsCommand;
    }

    public String[] getArgsCommandType() {
        return argsCommandType;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public String getAlias() {
        return alias;
    }

    public ExecutorConstants getCommandType() {
        return commandType;
    }

    public static class ExecutorTaskBuilder {

        private String path;
        private String alias;
        private String command;
        private String[] argsCommand;
        private String[] argsCommandType;
        private long waitTime;
        private ExecutorConstants commandType;


        public ExecutorTaskBuilder(String path, String command, ExecutorConstants commandType, String alias) {
            this.path = path;
            this.command = command;
            this.commandType = commandType;
            this.alias = alias;
        }

        public ExecutorTaskBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public ExecutorTaskBuilder setCommand(String command) {
            this.command = command;
            return this;

        }

        public ExecutorTaskBuilder setArgsCommand(String[] argsCommand) {
            this.argsCommand = argsCommand;
            return this;

        }

        public ExecutorTaskBuilder setArgsCommandType(String[] argsCommandType) {
            this.argsCommandType = argsCommandType;
            return this;

        }

        public ExecutorTaskBuilder setWaitTime(long waitTime) {
            this.waitTime = waitTime;
            return this;

        }

        public ExecutorTaskBuilder setCommandType(ExecutorConstants commandType) {
            this.commandType = commandType;
            return this;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public ExecutorTask build() {
            return new ExecutorTask(this);
        }


    }
}
