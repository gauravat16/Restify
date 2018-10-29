# rest-lambda [![Build Status](https://travis-ci.org/gauravat16/rest-lambda.svg?branch=master)](https://travis-ci.org/gauravat16/rest-lambda)

Make any program/script a rest service!

Used Chain wide in <img src="https://pbs.twimg.com/profile_images/875789275668918273/7l9RDOnl_400x400.jpg" alt="drawing" width="50" height="50"/>.

This project is under development. Please feel free to fork and contribute! 😀


## How to use

When you first run the application following folder structure should be created. 

* /usr/local/var/rest-lambda/Logs
* /usr/local/var/rest-lambda/Configuration

## Setup security

Set user.user1, password.user1 in application.properties.

& Use vm option ```-Dspring.profiles.active=SecurityOn``` 

## Two ways to execute jobs

* Send **JSON** request to execute a job (POST)
* Hit REST API (GET) with alias name (Uses XML Configuration)

* ### Method 1 (JSON Request)

  URL Path - http://localhost:8080/lambda/post/execute

  * path - Path to the script/program

  * alias - name for this job

  * commandType - This is the command you use to execute the script eg. sh, java, bash

  * argsCommandType - args for commandType eg. -jar, -x

  * command - The name of the script you want to execute

  * argsCommand -  args for your program


  ```json
  {
      "path": "/Users/gaurav/Downloads/",
      "alias":"testPost",
      "commandType":"sh",
      "command":"TestScript.sh",
      "waitTime": "32"
  
  }
  ```

  Test it by this command - 

  ```bash
  curl --header "Content-Type: application/json" --request POST  --data '{"path": "/Users/gaurav/Downloads/", "alias":"testPost", "commandType":"sh", "command":"TestScript.sh", "waitTime": "32"}' http://localhost:8080/lambda/post/execute
  
  ```

* ### Method 2 (XML)

  ## Create the configuration

  Create a configuration file in _/usr/local/var/rest-lambda/Configuration_.

  **Name - rest_lambda_jobs.xml**

  **Content of the XML -** 

  ```xml
       <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <ns2:restConfiguration xmlns:ns2="com.gaurav.rest.lambda.configuration">
          <restJobs>
          <!--Rest Job with custom command type-->
              <restJob>
                  <args-command>dataForScript</args-command>
                  <args-command-type>-x</args-command-type>
                  <alias>testJob1</alias>
                  <command>TestScript.sh</command>
                  <commandType>bash</commandType>
                  <path>/Users/gaurav/Downloads/</path>
                  <waitTime>20</waitTime>
              </restJob>

               <!--Rest Job with Batch (Windows) command type-->
              <restJob>
                  <args-command>dataForScript_1</args-command>
                  <args-command>dataForScript_2</args-command>
                  <args-command-type>/c</args-command-type>
                  <alias>testJob2</alias>
                  <command>TestBatch.bat</command>
                  <commandType>BATCH</commandType>
                  <path>C://testbat.bat</path>
                  <waitTime>20</waitTime>
              </restJob>

                   <!--Rest Job with Java command type-->
              <restJob>
                  <args-command>dataForScript_1</args-command>
                  <args-command>dataForScript_2</args-command>
                  <args-command-type>-jar</args-command-type>
                  <alias>testJob3</alias>
                  <command>/testJar.jar</command>
                  <commandType>JAVA</commandType>
                  <path>/usr/local/var</path>
                  <waitTime>20</waitTime>
              </restJob>
          </restJobs>
      </ns2:restConfiguration>
  ```



  ## Executing the script

  * curl http://localhost:8080/lambda/get/execute/alias 

  * example - curl http://localhost:8080/lambda/get/execute/bashTest
    [You can also hit the URL in a browser]

  * output - 

      ```json
      {"processExecCode":"0","output":"Hello!\n"}
      ```

      ### Refreshing configuration after adding a new rest job
      * To refresh configuration without restarting the app,

      * hit - curl http://localhost:8080/lambda/refresh 

      * Output -

        ```json
         {"processExecCode":"0","output":"Configuration Refreshed"}
        ```


  ## Scripts supported

  * Java
  * Python
  * Shell
  * Custom - provide executor program name in <commandType></commandType> ex - sh for Bash or gcc for C/CPP



## Executing rest-lambda
 **Natively -** 
 * Clone the project and run ./gradlew build bootJar
 * java -Dserver.port=**port** -jar build/libs/rest-lambda-**version-number**.jar 
 eg - java -Dserver.port=9090 -jar build/libs/rest-lambda-1.0.jar 
 
 **Docker -** 
  * Clone the project and run ./gradlew copyJarForDocker
  * cd ../rest-lambda/
  * docker-compose up
  * http://localhost:9091/lambda/get/execute/alias

