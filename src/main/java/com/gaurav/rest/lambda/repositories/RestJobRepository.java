/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.repositories;

import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestJobRepository extends MongoRepository<RestJobPostBean, String> {

    List<RestJobPostBean> findByAlias(String alias);

}