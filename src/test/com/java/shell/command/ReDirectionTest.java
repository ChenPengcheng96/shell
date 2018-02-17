package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;
import org.junit.*;

import java.io.*;
import java.util.Optional;

public class ReDirectionTest {
    private Shell shell;
    @BeforeClass
    public static void createTestFiles(){
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.createFiles(new File(tmpdir+"\\root"));
    }
    @AfterClass
    public static void deleteTestFiles(){
        String tmpdir = System.getProperty("java.io.tmpdir");
        TestUtil.deleteFiles(new File(tmpdir+"\\root"));
    }

    @Before
    public void before(){
        shell = new Shell();
        String dirname = System.getProperty("java.io.tmpdir")+"\\root";
        shell.setDir(new File(dirname));
    }

    @After
    public void after(){

    }

    @Test
    public void reOutputTest(){
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        String filename = shell.getDir().getAbsolutePath()+"\\123.txt";
        args.redirectOutputArg = Optional.of(filename);
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandConcatenate cmd = new CommandConcatenate(shell,args,input,output);
        String expected = "123456";
        cmd.run();
        String actual = cmd.getOutput().toString().trim();
        Assert.assertEquals(expected,actual);
        cmd.destroy();
    }

    @Test
    public void reInputTest(){
        Parser.CmdLineArgs args = new Parser.CmdLineArgs();
        String filename = shell.getDir().getAbsolutePath()+"\\456.txt";
        args.redirectInputArg = Optional.of(filename);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CommandList cmd = new CommandList(shell,args,null,output);
        String expected = "123.txt\t456.txt\tdir1";
        cmd.run();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String actual = null;
        try {
            actual = br.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cmd.destroy();
        Assert.assertEquals(expected,actual);
    }
}
