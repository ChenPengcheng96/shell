package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CommandList Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 15, 2018</pre>
 */
public class CommandListTest {
    private Shell shell;

    @Before
    public void before() throws Exception {
        shell = new Shell();
        File tmpdir = new File(System.getProperty("java.io.tmpdir")+"\\root");
        shell.setDir(tmpdir);
        TestUtil.createFiles();
    }
    @AfterClass
    public static void deleteDemoFiles() throws IOException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.deleteFiles(new File(tmpdir+"\\root"));
    }
    @After
    public void after() throws Exception {
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
    @Test
    public void testListCurrentDirectory() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("ls"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "123.txt\tdir1";
        cmd.run();
        String actual = output.toString().trim();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListWithA() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("ls -a"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "123.txt\tdir1\tdir1Hidden";
        cmd.run();
        String actual = output.toString().trim();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListWithL() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("ls -l"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "6 123.txt\n" + "0 dir1\n";
        cmd.run();
        String regex = "\\s+[A-Z][a-z][a-z] \\d\\d \\d\\d:\\d\\d\\s+";
        String actual = output.toString().replaceAll(regex," ");
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testListWithAL() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("ls -al"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String regex = "\\s+[A-Z][a-z][a-z] \\d\\d \\d\\d:\\d\\d\\s+";
        String expected = "6 123.txt\n" + "0 dir1\n" + "0 dir1Hidden";
        cmd.run();
        String actual = output.toString().replaceAll(regex," ").trim();
        Assert.assertEquals(expected,actual);
    }
}
