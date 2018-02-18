package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * CommandEcho Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 18, 2018</pre>
 */
public class CommandEchoTest {
    Shell shell;
    @Before
    public void before() throws Exception {
        shell = new Shell();
        File tmpdir = new File(System.getProperty("java.io.tmpdir")+"\\root");
        shell.setDir(tmpdir);
        TestUtil.createFiles();

    }

    @After
    public void after() throws Exception {
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.deleteFiles(new File(tmpdir+"\\root"));
    }

    /**
     * Method: getCommandName()
     */
    @Test
    public void testGetCommandName() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: run()
     */
    //1、echo    ---- \n
    //2、echo $USERNAME  ---- System.getEnv("USERNAME")+\n
    //3、echo -n a -n -s   -abc  ------ a -s -a -b -c
    @Test
    public void testWithNothing() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("echo"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CommandEcho cmd = new CommandEcho(shell,args,null,bos);
        String expected = "\n";
        cmd.run();
        String actual = cmd.getOutput().toString();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testWithEnv() throws Exception {
//TODO: Test goes here...
        String expected = System.getenv("USERNAME");
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("echo $USERNAME"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CommandEcho cmd = new CommandEcho(shell,args,null,bos);
        cmd.run();
        String actual = cmd.getOutput().toString().trim();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testWithManyString() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("echo -n a -n -s   -abc"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CommandEcho cmd = new CommandEcho(shell,args,null,bos);
        cmd.run();
        String expected = "a -n -s -abc";
        String actual = cmd.getOutput().toString().trim();
        Assert.assertEquals(expected,actual);
    }
} 
