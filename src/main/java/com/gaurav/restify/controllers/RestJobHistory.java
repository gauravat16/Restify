package com.gaurav.restify.controllers;

import com.gaurav.restify.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.restify.services.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestJobHistory {


    @Autowired
    DatabaseService databaseService;

    @RequestMapping(value = "restify/history/{alias}")
    private List<RestJobPostBean> getHistory(@PathVariable String alias) {

        return databaseService.getRestPostHistory(alias);


    }
}
