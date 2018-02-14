package com.shell.parser;

import com.shell.Shell;
import com.shell.command.*;

import java.io.*;
import java.util.*;

//命令解析器
//1、解析命令
public class Parser implements IParser {
    private Shell shell;

    public Parser(Shell shell) {
        this.shell = shell;
    }

    @Override
    public ICommand parse(String line) throws IOException {
        List<List<String>> commandLines = new ArrayList<>();
        splitCommandLines(line,commandLines);
        if(commandLines.size()>1) {
            List<SingleCommand> commandList = new ArrayList<>();
            for(List<String> wordList:commandLines){
                SingleCommand cmd = parseSingleCommand(wordList);
                commandList.add(cmd);
            }
            return new PipedCommand(commandList);
        }
        else
            return parseSingleCommand(commandLines.get(0));

    }


    private void splitCommandLines(String line,List<List<String>> commandLines){
        String[] strs = line.split("\\|");
        for(String s:strs){
            List<String> list = new ArrayList<>(Arrays.asList(s.trim().split(" ")));
            commandLines.add(list);
        }
    }
    private SingleCommand parseSingleCommand(List<String> wordList) throws IOException {//解析命令
        String cmd = wordList.get(0);
        CmdLineArgs args = parseArgs(wordList);
        InputStream input = parseInputReDirection(args);
        OutputStream output = parseOutPutReDirection(args);
        SingleCommand command = null;
        switch (cmd) {
            case "":
                command = null;
                break;
            case "cd":
                command = new CommandChangeDirectory(shell, args, input, output);
                break;
            case "pwd":
                command = new CommandPrintWorkDirectory(shell, args, input, output);
                break;
            case "ls":
                command = new CommandList(shell, args, input, output);
                break;
            case "cat":
                command = new CommandConcatenate(shell, args, input, output);
                break;
            default:
                System.out.println("'" + cmd + "'" + "不是外部命令，也不是可运行的程序或批处理文件。");
                break;
        }
        return command;
    }

    private CmdLineArgs parseArgs(List<String> wordList) {//解析参数
        CmdLineArgs args = new CmdLineArgs();
        for (int i = 1; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if (s.matches("--[a-zA-Z]+"))
                args.mapArg.put(s, wordList.get(++i));
            else if (">".equals(s))
                args.redirectOutputArg = Optional.ofNullable(wordList.get(++i));
            else if ("<".equals(s))
                args.redirectInputArg = Optional.ofNullable(wordList.get(++i));
            else if(s.startsWith("-")) {
                char[] chars = s.toCharArray();
                for (int j = 1; j < chars.length; j++)
                    args.singleArg.add("-" + chars[j]);
            }
            else
                args.directory.add(s);
        }
        return args;
    }

    private OutputStream parseOutPutReDirection(CmdLineArgs args) throws IOException {//解析输出模式
        if (args.redirectOutputArg.isPresent()) {
            String filename = args.redirectOutputArg.get();
            if (!filename.matches("[a-zA-Z]:.*?"))
                filename = shell.getDir().getCanonicalPath() + "\\" + filename;
            File f = new File(filename);
            if (f.exists())
                f.delete();
            f.createNewFile();
            return new FileOutputStream(f);
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
        return System.in;
    }

    public static class CmdLineArgs {
        public Map<String, String> mapArg = new HashMap<>();
        public List<String> singleArg = new ArrayList<>();
        public List<String> directory = new ArrayList<>();
        public Optional<String> redirectOutputArg = Optional.empty();
        public Optional<String> redirectInputArg = Optional.empty();

    }
}



