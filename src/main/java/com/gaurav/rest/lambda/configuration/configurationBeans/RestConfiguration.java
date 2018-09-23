package com.gaurav.rest.lambda.configuration.configurationBeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(namespace = "com.gaurav.restify.configuration")
public class RestConfiguration {


    @XmlElementWrapper(name = "restJobs")

    @XmlElement(name = "restJob")
    private ArrayList<RestJob> restJobs;


    public ArrayList<RestJob> getJobs() {
        return restJobs;
    }

    public void setRestJobs(ArrayList<RestJob> restJobs) {
        this.restJobs = restJobs;
    }


}
