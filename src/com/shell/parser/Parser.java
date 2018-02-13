package com.shell.parser;

import com.shell.Shell;
import com.shell.command.*;

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
        String cmd = wordList.get(0);
        CmdLineArgs args = parseArgs();
        InputStream input = parseInputReDirection(args);
        OutputStream output = parseOutPutReDirection(args);

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

    private CmdLineArgs parseArgs() {//解析参数
        CmdLineArgs args = new CmdLineArgs();
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if (s.matches("-[a-zA-Z]"))
                args.singleArg.add(s);
            else if (">".equals(s))
                args.redirectOutputArg = Optional.ofNullable(wordList.get(i + 1));

            else if ("<".equals(s))
                args.redirectInputArg = Optional.ofNullable(wordList.get(i + 1));
            else if(s.startsWith("-"))
                args.mapArg.put(s, wordList.get(i + 1));
        }
        return args;

    }

    private OutputStream parseOutPutReDirection(CmdLineArgs args) throws IOException {//解析输出模式
        //设置输出设备，默认为0---控制台
        if (args.redirectOutputArg.isPresent()) {
            File f = new File(args.redirectOutputArg.get());
            if (f.exists())
                f.delete();
            f.createNewFile();
            return new FileOutputStream(f);
//        } else if () {
//            File f = new File();
//            if (!f.exists())
//                f.createNewFile();
//            return new FileOutputStream(f);
        }

        return System.out;
    }

    private InputStream parseInputReDirection(CmdLineArgs args) throws IOException {
        if (args.redirectInputArg.isPresent()) {
            File f = new File(args.redirectInputArg.get());
            if (f.exists())
                f.delete();
            f.createNewFile();
            return new FileInputStream(f);
        }
        return null;
    }

    public static class CmdLineArgs {
        public Map<String, String> mapArg = new HashMap<>();
        public List<String> singleArg = new ArrayList<>();
        public Optional<String> redirectOutputArg = Optional.empty();
        public Optional<String> redirectInputArg = Optional.empty();

    }
}



