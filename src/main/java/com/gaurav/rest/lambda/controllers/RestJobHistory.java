/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.controllers;

import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.rest.lambda.services.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestJobHistory {


    @Autowired
    DatabaseService databaseService;

    @RequestMapping(value = "lambda/history/alias/{alias}")
    private List<RestJobPostBean> getHistory(@PathVariable String alias) {

        return databaseService.getRestPostHistoryByAlias(alias);

    }

    @RequestMapping(value = "lambda/history")
    private List<RestJobPostBean> getHistory(@RequestParam("page") int page) {

        List<RestJobPostBean> beans = new ArrayList<>();
        databaseService.findAllPaginated(PageRequest.of(page, 10)).forEach(restJobPostBean -> beans.add(restJobPostBean));

        return beans;

    }


}
