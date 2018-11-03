/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.services.database;

import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.rest.lambda.repositories.RestJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class DatabaseService {

    @Autowired
    RestJobRepository restJobRepository;

    @Async
    public void addJob(RestJobPostBean restJobPostBean) {
        restJobRepository.save(restJobPostBean);
    }

    public List<RestJobPostBean> getRestPostHistoryByAlias(String alias) {
        return restJobRepository.findByAlias(alias);
    }

    public Iterable<RestJobPostBean> findAllPaginated(Pageable pageRequest){
        return restJobRepository.findAll(pageRequest);
    }



}
