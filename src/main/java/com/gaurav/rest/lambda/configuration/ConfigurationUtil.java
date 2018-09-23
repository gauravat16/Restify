package com.gaurav.rest.lambda.configuration;

import com.gaurav.rest.lambda.configuration.configurationBeans.RestConfiguration;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestJob;

import javax.xml.bind.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ConfigurationUtil {

    public static void objectToXML(Class clazz, Object clazzObj, Path path) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(clazz.cast(clazzObj), path.toFile());


    }


    public static Object XMLtoObject(Class clazz, Path path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        return um.unmarshal(new FileReader(path.toFile().getAbsolutePath()));

    }


    //Test Function
    public static void main(String[] args) {
        RestJob restJob = new RestJob();

        restJob.setAlias("testJob");
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("BASH");
        restJob.setPath("/Users/gaurav/Downloads");
        restJob.setWaitTime(20);
        restJob.setArgsCommand(new String[]{"dataForScript"});
        restJob.setArgsCommandType(new String[]{"-c", "root"});


        RestConfiguration restConfiguration = new RestConfiguration();
        ArrayList<RestJob> restJobs = new ArrayList<>();
        restJobs.add(restJob);
        restConfiguration.setRestJobs(restJobs);
        try {
            objectToXML(RestConfiguration.class, restConfiguration, Paths.get("/usr/local/var/Restify/Configuration/testCOnf.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
