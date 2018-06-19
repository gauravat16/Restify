package com.gaurav.restify.beans;

import com.gaurav.restify.constants.ExecutorConstants;

public class ExecutorTask {
    private String path;
    private ExecutorConstants commandType;
    private String command;
    private String alias;
    private String[] args;
    private long waitTime;

    private ExecutorTask(ExecutorTaskBuilder executorTaskBuilder) {

        this.path = executorTaskBuilder.path;
        this.command = executorTaskBuilder.command;
        this.args = executorTaskBuilder.args;
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

    public String[] getArgs() {
        return args;
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
        private String[] args;
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

        public ExecutorTaskBuilder setArgs(String[] args) {
            this.args = args;
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
