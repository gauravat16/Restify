package com.gaurav.restify.services.database;

import com.gaurav.restify.beans.dbpostbeans.RestJobPostBean;
import com.gaurav.restify.repositories.RestJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    RestJobRepository restJobRepository;

    public void addJob(RestJobPostBean restJobPostBean) {
        restJobRepository.save(restJobPostBean);
    }


}
