package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.*;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CommandConcatenate Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 15, 2018</pre>
 */
public class CommandConcatenateTest {
    private Shell shell;
    @Before
    public void before() {
        shell = new Shell();
        File tmpdir = new File(System.getProperty("java.io.tmpdir")+"\\root");
        shell.setDir(tmpdir);
        TestUtil.createFiles();
    }

    @After
    public void after() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.deleteFiles(new File(tmpdir+"\\root"));
    }

    /**
     * Method: run()
     */
    //cat
    @Test
    public void testSingleCat() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("cat"));
        String expected = "Hello,world!\n";
        ByteArrayInputStream input = new ByteArrayInputStream(expected.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandConcatenate cmd = new CommandConcatenate(shell,args,input,output);
        cmd.run();
        String actual = output.toString();
        Assert.assertEquals(expected, actual);
    }


    //cat 123.txt
    @Test
    public void testCatFile() throws Exception {
//TODO: Test goes here...
        String filename = System.getProperty("java.io.tmpdir")+"\\root\\123.txt";
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("cat"+filename));
        FileInputStream input = new FileInputStream(filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandConcatenate cmd = new CommandConcatenate(shell,args,input,output);
        cmd.run();
        String actual = output.toString();
        cmd.destroy();
        String expected = "123456\n";
        Assert.assertEquals(expected, actual);
    }
}
