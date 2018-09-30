/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.services.database;

import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.rest.lambda.repositories.RestJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class DatabaseService {


    @Value("${spring.data.mongodb.uri}")
    String MONGO_DB_URI;

    @Value("${spring.data.mongodb.database}")
    String MONGO_DB_NAME;

    @Autowired
    RestJobRepository restJobRepository;

    @Async
    public void addJob(RestJobPostBean restJobPostBean) {
        restJobRepository.save(restJobPostBean);
    }

    public List<RestJobPostBean> getRestPostHistory(String alias) {
        return restJobRepository.findByAlias(alias);
    }



}
