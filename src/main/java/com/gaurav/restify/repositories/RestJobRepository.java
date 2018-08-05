package com.gaurav.restify.repositories;

import com.gaurav.restify.beans.dbpostbeans.RestJobPostBean;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestJobRepository extends MongoRepository<RestJobPostBean, String> {
}