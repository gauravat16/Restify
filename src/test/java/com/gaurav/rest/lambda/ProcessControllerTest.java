package com.gaurav.rest.lambda;

import com.gaurav.rest.lambda.beans.Response;
import com.gaurav.rest.lambda.configuration.RestConfigurationManager;
import com.gaurav.rest.lambda.configuration.configurationBeans.RestJob;
import com.gaurav.rest.lambda.controllers.ProcessController;
import com.gaurav.rest.lambda.services.executor.ExecutorService;
import org.apache.commons.exec.ExecuteException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests
 *
 * @author gaurav
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessControllerTest {

    @Spy
    @InjectMocks
    protected ProcessController processController = new ProcessController();

    @Spy
    protected ExecutorService executorService = new ExecutorService();

    protected RestConfigurationManager restConfigurationManager = RestConfigurationManager.getInstance();

    protected RestJob restJob;

    protected File testScriptFile;

    @Before
    public void setup() throws FileNotFoundException {
        restJob = new RestJob();
        testScriptFile = ResourceUtils.getFile("classpath:test_scripts/TestScript.sh");

    }

    /**
     * TestScript.sh
     *
     * @throws IOException
     */

    @Test
    public void testExecScriptPost() throws IOException {
        restJob.setAlias("testExecScriptPost");
        restJob.setArgsCommand(new String[]{});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");

        Response response = processController.executeScripts(restJob);
        assertEquals(response.getProcessExecCode(), "0");
        assertEquals(response.getOutput().trim(), "Value-");
    }


    @Test
    public void testExecScriptPostArgs() throws IOException {
        restJob.setAlias("testExecScriptPost");
        restJob.setArgsCommand(new String[]{"testval"});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");

        Response response = processController.executeScripts(restJob);
        assertEquals(response.getProcessExecCode(), "0");
        assertEquals(response.getOutput().trim(), "Value-testval");
    }


    @Test
    public void testExecScriptPostError() throws IOException {
        restJob.setAlias("testExecScriptPost");
        restJob.setArgsCommand(new String[]{"error"});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");

        Response response = null;

        try {
            response = processController.executeScripts(restJob);

        } catch (ExecuteException e) {
            assertEquals(e.getExitValue(), 10);

        }

        assertEquals(response, null);

    }


    @Test
    public void testExecScriptGet() throws IOException {
        restJob.setAlias("testExecScriptGet");
        restJob.setArgsCommand(new String[]{});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");
        restConfigurationManager.addRestJob(restJob);

        Response response = processController.executeScripts("testExecScriptGet");
        assertEquals(response.getProcessExecCode(), "0");
        assertEquals(response.getOutput().trim(), "Value-");
    }

    @Test
    public void testExecScriptGetTimedFail() throws IOException {
        restJob.setAlias("testExecScriptGet");
        restJob.setArgsCommand(new String[]{});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(2);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");
        restConfigurationManager.addRestJob(restJob);

        Response response = null;

        try {
            response = processController.executeScripts(restJob);

        } catch (ExecuteException e) {
            assertEquals(e.getExitValue(), 143);

        }

        assertEquals(response, null);
    }


    @Test
    public void testExecScriptGetArgs() throws IOException {
        restJob.setAlias("testExecScriptGet");
        restJob.setArgsCommand(new String[]{"testval"});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");
        restConfigurationManager.addRestJob(restJob);

        Response response = processController.executeScripts("testExecScriptGet");
        assertEquals(response.getProcessExecCode(), "0");
        assertEquals(response.getOutput().trim(), "Value-testval");
    }

    @Test
    public void testExecScriptGetError() throws IOException {
        restJob.setAlias("testExecScriptGet");
        restJob.setArgsCommand(new String[]{"error"});
        restJob.setArgsCommandType(new String[]{});
        restJob.setPath(testScriptFile.getParent());
        restJob.setWaitTime(30);
        restJob.setCommand("TestScript.sh");
        restJob.setCommandType("sh");

        restConfigurationManager.addRestJob(restJob);
        Response response = null;

        try {
            response = processController.executeScripts(restJob);

        } catch (ExecuteException e) {
            assertEquals(e.getExitValue(), 10);

        }

        assertEquals(response, null);

    }
}
