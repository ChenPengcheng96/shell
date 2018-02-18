package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * PipedCommand Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 16, 2018</pre>
 */
public class PipedCommandTest {
    private Shell shell;
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
     * Method: run()
     */
    @Test
    //ls | cat
    public void testRun() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs arg1 = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("ls"));
        Parser.CmdLineArgs arg2 = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("cat"));
        SingleCommand cmd1 = new CommandList(shell,arg1,null,System.out);
        ByteArrayInputStream bis = new ByteArrayInputStream(cmd1.getOutput().toString().getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SingleCommand cmd2 = new CommandConcatenate(shell,arg2,bis,bos);
        List<SingleCommand> cmdList = new ArrayList<>();
        cmdList.add(cmd1);
        cmdList.add(cmd2);
        PipedCommand pipedcmd = new PipedCommand(cmdList);
        String expected = "123.txt\tdir1";
        pipedcmd.run();
        String actual = cmd2.getOutput().toString().trim();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Method: destroy()
     */
    @Test
    public void testDestory() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }

} 
