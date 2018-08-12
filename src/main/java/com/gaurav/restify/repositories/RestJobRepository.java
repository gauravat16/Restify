package com.gaurav.restify.repositories;

import com.gaurav.restify.beans.dbpostbeans.RestJobPostBean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestJobRepository extends MongoRepository<RestJobPostBean, String> {

    List<RestJobPostBean> findByAlias(String alias);

}