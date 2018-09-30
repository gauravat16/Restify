/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.controllers;

import com.gaurav.rest.lambda.beans.Response;
import com.gaurav.rest.lambda.configuration.RestConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshConfiguration {

    @Autowired
    RestConfigurationManager restConfigurationManager;

    @RequestMapping(value = "/lambda/refresh")
    public Response refreshConfiguration(){

        restConfigurationManager.refreshConfiguration();

        Response response = new Response(String.valueOf(0));
        response.setOutput("Configuration Refreshed");

        return response;

    }
}
