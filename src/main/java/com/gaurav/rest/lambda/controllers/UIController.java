/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {


    @RequestMapping(value = "/lambda/dashboard")
    String openDashboard(){
        return "/html/SendQuery.html";
    }
}
