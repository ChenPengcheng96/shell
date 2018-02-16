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

    @BeforeClass
    public static void createDemoFiles() throws IOException {
        String temDirectory = System.getProperty("java.io.tmpdir");
        File root = new File(temDirectory+"root");
        if(root.exists())
            TestUtil.deleteFiles(root);
        TestUtil.createFiles(root);
    }
    @Before
    public void before() throws Exception {
        shell = new Shell();
        String dirname = System.getProperty("java.io.tmpdir")+"\\root";
        shell.setDir(new File(dirname));
    }
    @AfterClass
    public static void deleteDemoFiles() throws IOException {
        String temDirectory = System.getProperty("java.io.tmpdir");
        File root = new File(temDirectory+"root");
        if(root.exists())
            TestUtil.deleteFiles(root);
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
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        args.setParameter(new ArrayList<>());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "dir1";
        cmd.run();
        String actual = output.toString().trim();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListWithA() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        args.setParameter(new ArrayList<>());
        ArrayList<String> singleArg = new ArrayList<>();
        singleArg.add("-a");
        args.setOptionWithoutValue(singleArg);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "dir1\tdir1Hidden";
        cmd.run();
        String actual = output.toString().trim();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListWithL() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        args.setParameter(new ArrayList<>());
        ArrayList<String> singleArg = new ArrayList<>();
        singleArg.add("-l");
        args.setOptionWithoutValue(singleArg);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String expected = "0dir1";
        cmd.run();
        String[] tem = output.toString().split("\\t+");
        String actual = tem[0].trim() + tem[2].trim();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testListWithAL() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        args.setParameter(new ArrayList<>());
        ArrayList<String> singleArg = new ArrayList<>();
        singleArg.add("-a");
        singleArg.add("-l");
        args.setOptionWithoutValue(singleArg);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandList cmd = new CommandList(shell, args, null, output);
        String regex = "\\s+[A-Z][a-z][a-z] \\d\\d \\d\\d:\\d\\d\\s+";
        String expected = "0 dir1\n0 dir1Hidden";
        cmd.run();
        String actual = output.toString().replaceAll(regex," ").trim();
        Assert.assertEquals(expected,actual);
    }
}
