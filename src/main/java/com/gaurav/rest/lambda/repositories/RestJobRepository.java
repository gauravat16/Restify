/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.repositories;

import com.gaurav.rest.lambda.beans.dbpostbeans.RestJobPostBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestJobRepository extends PagingAndSortingRepository<RestJobPostBean, String> {

    List<RestJobPostBean> findByAlias(String alias);

    Page<RestJobPostBean> findAll(Pageable pageable);
}
