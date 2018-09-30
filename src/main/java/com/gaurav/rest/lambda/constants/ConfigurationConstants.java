/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.constants;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface ConfigurationConstants {


    String DEFAULT_CONFIG_DIR_PATH_LINUX = "/usr/local/var/rest-lambda/Configuration";
    String DEFAULT_LOG_DIR_PATH_LINUX = "/usr/local/var/rest-lambda/Logs";
    String DEFAULT_CONFIG_FILE_NAME = "rest_lambda_jobs.xml";

    Path REST_CONFIG_FILE = Paths.get(DEFAULT_CONFIG_DIR_PATH_LINUX + File.separator + DEFAULT_CONFIG_FILE_NAME);
    Path REST_LOG_PATH = Paths.get(DEFAULT_LOG_DIR_PATH_LINUX);
    Path REST_CONFIG_PATH = Paths.get(DEFAULT_CONFIG_DIR_PATH_LINUX);

}
