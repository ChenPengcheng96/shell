package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.*;

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
    }

    @After
    public void after() {
    }

    /**
     * Method: run()
     */
    @Test
    public void testSingleCat() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        String expected = "Hello,world!\n";
        ByteArrayInputStream input = new ByteArrayInputStream(expected.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandConcatenate cmd = new CommandConcatenate(shell,args,input,output);
        cmd.run();
        String actual = output.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCatFile() throws Exception {
//TODO: Test goes here...
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        List<String> directoryList = new ArrayList<>();
        String filename = "E:\\tem\\123.txt";
        File file = new File(filename);
        file.createNewFile();
        directoryList.add(filename);
        args.setDirectory(directoryList);
        PrintWriter pw = new PrintWriter(filename,"utf-8");
        pw.print("Hello,World");
        pw.close();
        FileInputStream input = new FileInputStream(filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandConcatenate cmd = new CommandConcatenate(shell,args,input,output);
        cmd.run();
        String actual = output.toString();
        String expected = "Hello,World\n";
        Assert.assertEquals(expected, actual);
        file.delete();
    }
}
