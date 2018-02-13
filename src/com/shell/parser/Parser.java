package com.shell.parser;

import com.shell.Shell;
import com.shell.command.ICommand;
import com.shell.command.CommandChangeDirectory;
import com.shell.command.CommandList;
import com.shell.command.CommandPrintWorkDirectory;

import java.io.*;
import java.util.*;

//命令解析器
//1、解析命令
public class Parser implements IParser {
    private static List<String> wordList;//命令行字符串数组
    private Shell shell;

    public Parser(Shell shell) {
        this.shell = shell;
    }

    public ICommand parse(String line) throws IOException {//解析命令
        String[] words = line.split(" ");
        wordList = new ArrayList<String>(Arrays.asList(words));
        String cmd = wordList.remove(0);
        List<String> args = parseArgs();
        InputStream input = parseInputReDirection();
        OutputStream output = parseOutPutReDirection();

        ICommand command = null;
        switch (cmd) {
            case "cd":
                command = new CommandChangeDirectory(shell, args, input, output);
                break;
            case "pwd":
                command = new CommandPrintWorkDirectory(shell, args, input, output);
                break;
            case "ls":
                command = new CommandList(shell, args, input, output);
                break;
            default:
                System.out.println("'" + cmd + "'" + "不是外部命令，也不是可运行的程序或批处理文件。");
                break;
        }
        return command;
    }

    private List<String> parseArgs() {//解析参数
        List<String> argList = new ArrayList<String>();
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if (s.startsWith("-")) {
                argList.add(s);
                wordList.remove(i);
            }
        }
        return argList;
    }

    private OutputStream parseOutPutReDirection() throws IOException {//解析输出模式
        //设置输出设备，默认为0---控制台
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if (s.equals(">")) {
                File f = new File(wordList.get(i + 1));
                if (f.exists())
                    f.delete();
                f.createNewFile();
                wordList.remove(i);
                wordList.remove(i + 1);
                return new FileOutputStream(f);
            } else if (s.equals(">>")) {
                File f = new File(wordList.get(i + 1));
                if (!f.exists())
                    f.createNewFile();
                wordList.remove(i);
                wordList.remove(i + 1);
                return new FileOutputStream(f);
            }
        }
        return System.out;
    }

    private InputStream parseInputReDirection() throws IOException {
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if (s.equals("<")) {
                File f = new File(wordList.get(i + 1));
                if (f.exists())
                    f.delete();
                f.createNewFile();
                wordList.remove(i);
                wordList.remove(i + 1);
                return new FileInputStream(f);
            }
        }
        return null;
    }

    public static List<File> parseDirectory() {//解析文件夹（工具命令）
        List<File> fileList = new ArrayList<>();
        File f;
        for (int i = 0; i < wordList.size(); i++) {
            String filename = wordList.get(i);
            f = new File(filename);
            if (f.exists()) {
                fileList.add(f);
                wordList.remove(i);
            }
        }
        return fileList;
    }
}

