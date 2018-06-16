package com.gaurav.restify.configuration.configurationBeans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;

@XmlRootElement(name = "RestJob")

@XmlType
public class RestJob {

    private String path;
    private String commandType;
    private String command;
    private String[] args;
    private long waitTime;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "RestJob{" +
                "path='" + path + '\'' +
                ", commandType='" + commandType + '\'' +
                ", command='" + command + '\'' +
                ", args=" + Arrays.toString(args) +
                ", waitTime=" + waitTime +
                '}';
    }
}
