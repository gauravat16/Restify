package com.gaurav.restify.configuration;

import javax.xml.bind.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;


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

}
