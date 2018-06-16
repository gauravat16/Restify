package com.gaurav.restify.controllers;

import com.gaurav.restify.beans.Response;
import com.gaurav.restify.configuration.RestConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshConfiguration {

    @Autowired
    RestConfigurationManager restConfigurationManager;

    @RequestMapping(value = "/refresh")
    public Response refreshConfiguration(){

        restConfigurationManager.refreshConfiguration();

        Response response = new Response(String.valueOf(0));
        response.setOutput("Configuration Refreshed");

        return response;

    }
}
