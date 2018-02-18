package com.java.shell.command;

import com.java.shell.Shell;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.java.shell.command.TestUtil.createCommandFromArgs;
import static com.java.shell.command.TestUtil.createFiles;
import static com.java.shell.command.TestUtil.deleteFiles;

/**
 * CommandChangeDirectory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 15, 2018</pre>
 */
public class CommandChangeDirectoryTest {

    private Shell shell;

    @Before
    public void before() {
        shell = new Shell();
        File tmpdir = new File(System.getProperty("java.io.tmpdir")+"\\root");
        shell.setDir(tmpdir);
        TestUtil.createFiles();
    }

    @After
    public void after(){
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.deleteFiles(new File(tmpdir+"\\root"));
    }

    /**
     * Method: getCommandName()
     */
    @Test
    public void testGetCommandName(){
        Shell shell = new Shell();
        CommandChangeDirectory cmd = createCommandFromArgs(shell, "..");
        Assert.assertEquals("cd", cmd.getCommandName());
    }

    /**
     * Method: run()
     */
    @Test
    public void testCdToParentFolder() throws Exception {
//TODO: Test goes here...
        String excepted = shell.getDir().getParent();
        CommandChangeDirectory cmd = createCommandFromArgs(shell, "cd ..");
        cmd.run();
        String actual = shell.getDir().getAbsolutePath();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void testCdToAbsolutePath() throws Exception {
//TODO: Test goes here...
        String excepted = "E:\\Documents";
        CommandChangeDirectory cmd = createCommandFromArgs(shell, "cd E:\\Documents");
        cmd.run();
        String actual = shell.getDir().getAbsolutePath();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void testCdToCurrentFolder() throws Exception {
//TODO: Test goes here...
        String excepted = shell.getDir().getAbsolutePath();
        CommandChangeDirectory cmd = createCommandFromArgs(shell, "cd .");
        cmd.run();
        String actual = shell.getDir().getAbsolutePath();
        Assert.assertEquals(excepted, actual);
    }
} 
