/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.configuration.configurationBeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;

@XmlRootElement(name = "RestJob")

@XmlType
public class RestJob {

    private String path;
    private String alias;
    private String commandType;
    private String command;
    @XmlElement(name = "args-command")
    private String[] argsCommand;
    @XmlElement(name = "args-command-type")
    private String[] argsCommandType;
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

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String[] getArgsForCommand() {
        return argsCommand;
    }

    public void setArgsCommand(String[] argsCommand) {
        this.argsCommand = argsCommand;
    }

    public String[] getArgsForCommandType() {
        return argsCommandType;
    }

    public void setArgsCommandType(String[] argsCommandType) {
        this.argsCommandType = argsCommandType;
    }

    @Override
    public String toString() {
        return "RestJob{" +
                "path='" + path + '\'' +
                ", alias='" + alias + '\'' +
                ", commandType='" + commandType + '\'' +
                ", command='" + command + '\'' +
                ", argsCommand=" + Arrays.toString(argsCommand) +
                ", argsCommandType=" + Arrays.toString(argsCommandType) +
                ", waitTime=" + waitTime +
                '}';
    }
}
