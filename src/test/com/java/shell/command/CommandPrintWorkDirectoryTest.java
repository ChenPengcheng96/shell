package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * CommandPrintWorkDirectory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 15, 2018</pre>
 */
public class CommandPrintWorkDirectoryTest {
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
    @Test
    public void testRun() throws IOException {
//TODO: Test goes here...
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,TestUtil.toWordList("pwd"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String expected = shell.getDir().getAbsolutePath()+"\n";
        CommandPrintWorkDirectory cmd = new CommandPrintWorkDirectory(shell,args,null,output);
        cmd.run();
        String actual = output.toString();
        Assert.assertEquals(expected,actual);
    }

} 
