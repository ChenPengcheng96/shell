package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestUtil {
    private TestUtil(){}
    public static CommandChangeDirectory createCommandFromArgs(Shell shell, String singleLine) {
        Parser.CmdLineArgs args = Parser.CmdLineArgs.parseParam(shell,Arrays.asList(singleLine.split(" ")));
        String inputString = "";
        InputStream input = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        OutputStream output = new ByteArrayOutputStream();
        return new CommandChangeDirectory(shell, args, input, output);
    }

    public static void deleteFiles(File f){
        if (f.isDirectory()) {
            for (File c : Objects.requireNonNull(f.listFiles()))
                deleteFiles(c);
        }
        if (!f.delete()){
            System.err.println(f.getName()+"文件删除失败");
        }

    }

    public static void createFiles(){
        String tmpdir = System.getProperty("java.io.tmpdir");
        File directory = new File(tmpdir+"\\root");
        if(!directory.exists())
            directory.mkdir();
        if(!directory.isDirectory())
            throw new RuntimeException(directory + " not a parameter");
        File dir = new File(directory + "\\dir1\\dir2");
        dir.mkdirs();
        try {
            new File(dir,"file.txt").createNewFile();
        } catch (IOException e) {
            System.err.println("文件创建失败");
            return;
        }
        File dirHidden = new File(directory+"\\dir1Hidden\\dir2Hidden");
        dirHidden.mkdirs();
        try {
            new File(dirHidden,"fileHidden.txt").createNewFile();
        } catch (IOException e) {
            System.err.println("文件创建失败");
            return;
        }
        try {
            Files.setAttribute(Paths.get(dirHidden.getParentFile().getAbsolutePath()), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println("设置隐藏文件失败");
            return;
        }
        createTestFile(directory);
    }

    public static void createTestFile(File directory){
        File file = new File(directory,"123.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("文件创建失败");
            }
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file,"utf-8");
        } catch (FileNotFoundException e) {
            System.err.println(file.getName()+"文件不存在");;
        } catch (UnsupportedEncodingException e) {
            System.err.println("不支持此编码");
        }
        if(pw!=null){
            pw.print("123456");
            pw.close();
        }
    }

    public static List<String> toWordList(String s){
        return Arrays.asList(s.trim().split("\\s+"));
    }
}
