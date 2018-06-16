# Restify
Make any program/script a rest service!

This project is under development. Please feel free to fork and contribute! 😀


### How to use Restify

When you first run the application following folder structure should be created. (The application will then exit)

* /usr/local/var/Restify/Logs
* /usr/local/var/Restify/Configuration

##### Create a configuration file in /usr/local/var/Restify/Configuration.
**Name - Restify_Rest_Jobs.xml**

**Content of the XML -** 

        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <ns2:restConfiguration xmlns:ns2="com.gaurav.restify.configuration">
            <restJobs>
                <restJob>
                    <args>val1<args>
                    <args>val2<args>
                    <command>Testscript.sh</command>
                    <commandType>BASH</commandType>
                    <path>/Users/gaurav/Downloads</path>
                    <waitTime>20</waitTime>
                </restJob>
                 <restJob>
                    <command>TestPython.py</command>
                    <commandType>PYTHON</commandType>
                    <path>/Users/gaurav/Downloads</path>
                    <waitTime>20</waitTime>
                </restJob>
            </restJobs>
        </ns2:restConfiguration>


##### Executing the script

* curl http://localhost:8080/execute/script-name 
* example - curl http://localhost:8080/execute/Testscript.sh
[You can also hit the URL in a browser]
* output - 
    **{"processExecCode":"0","output":"Hello!\n"}**


##### Refreshing configuration after adding a new rest job

* To refresh configuration without restarting the app,
* hit - curl http://localhost:8080/refresh 
* Output - **{"processExecCode":"0","output":"Configuration Refreshed"}**


