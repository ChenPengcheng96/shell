package com.java.shell.parser;

import com.java.shell.Shell;
import com.java.shell.command.*;

import java.io.*;
import java.util.*;

import static com.java.shell.parser.Parser.CmdLineArgs.parseParam;

public class Parser implements IParser {
    private Shell shell;

    public Parser(Shell shell) {
        this.shell = shell;
    }

    @Override
    public ICommand parse(String line) {//解析整行命令
        List<List<String>> commandLines = new ArrayList<>();
        splitCommandLines(line, commandLines);
        if (commandLines.size() > 1) {
            List<SingleCommand> commandList = new ArrayList<>();
            for (List<String> wordList : commandLines) {
                SingleCommand cmd = parseSingleCommand(wordList);
                commandList.add(cmd);
            }
            return new PipedCommand(commandList);
        } else
            return parseSingleCommand(commandLines.get(0));
    }

    private void splitCommandLines(String line, List<List<String>> commandLines) {//将一行命令分成命令最小单元
        String[] strs = line.split("\\|");
        for (String s : strs) {
            List<String> list = new ArrayList<>(Arrays.asList(s.trim().split("\\s+")));
            commandLines.add(list);
        }
    }

    private SingleCommand parseSingleCommand(List<String> wordList) {//解析单元命令
        String cmd = wordList.get(0);
        CmdLineArgs args = parseParam(shell, wordList);
        InputStream input = System.in;
        OutputStream output = System.out;
        if (args.redirectInputArg.isPresent()) {
            input = redirectInput(args.redirectInputArg.get());
        } else if (args.redirectOutputArg.isPresent()) {
            output = redirectOutput(args.redirectOutputArg.get());
        }
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
            case "set":
                command = new CommandSet(shell, args, input, output);
                break;
            case "echo":
                command = new CommandEcho(shell, args, input, output);
                break;
            default:
                System.out.println("'" + cmd + "'" + "不是外部命令，也不是可运行的程序或批处理文件。");
                break;
        }
        return command;
    }


    public static class CmdLineArgs {//静态内部类,将单元命令的数据结构
        public List<IParam> param = new ArrayList<>();
        public Map<String, String> optionWithValue = new HashMap<>();
        public List<String> optionWithoutValue = new ArrayList<>();
        public List<String> parameter = new ArrayList<>();
        public Optional<String> redirectOutputArg = Optional.empty();
        public Optional<String> redirectInputArg = Optional.empty();

        private CmdLineArgs(){}

        //将单元命令解析成命令CmdLineArgs
        public static CmdLineArgs parseParam(Shell shell,List<String> wordList){
            List<IParam> param = new ArrayList<>();
            for (int i = 1; i < wordList.size(); i++) {
                String s = wordList.get(i);
                if(s.startsWith("$"))
                    s = shell.getEnv().get(s.substring(1));
                if(s == null)
                    continue;
                if(s.startsWith("-")){
                    param.add(new OptionWithoutValue(s));
//                    char[] chars = s.toCharArray();
//                    for (int j = 1; j < chars.length; j++)
//                        param.add(new OptionWithoutValue("-" + chars[j]));
                }
                else if(s.startsWith("--"))
                    param.add(new OptionWithValue(s,wordList.get(++i)));
                else if(s.equals(">"))
                    param.add(new RedirectParam(RedirectParam.RedirectType.Output,wordList.get(++i)));
                else if(s.equals("<"))
                    param.add(new RedirectParam(RedirectParam.RedirectType.Input,wordList.get(++i)));
                else
                    param.add(new Parameter(s));
            }
            return CmdLineArgs.of(param);
        }

        //工厂函数：传入参数自动封装成CmdLineArgs类型，用在parseIParam
        static CmdLineArgs of(List<IParam> param) {
            CmdLineArgs args = new CmdLineArgs();
            args.param = param;
            for(IParam p : param){
                p.register(args);
            }
            return args;
        }
    }

    //用于重定向输入：将解析的重定向参数转变成文件输入流
    private InputStream redirectInput(String filename){
        if (filename != null) {
            if (!filename.matches("[a-zA-Z]:.*?"))
                filename = shell.getDir().getAbsolutePath() + "\\" + filename;
            File f = new File(filename);
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e) {
                System.err.println("文件不存在");
            }
        }
        return System.in;
    }

    //用于重定向输出：将解析的重定向参数转变成文件输出流
    private OutputStream redirectOutput(String filename){
        if (filename != null) {
            if (!filename.matches("[a-zA-Z]:.*?"))
                filename = shell.getDir().getAbsolutePath() + "\\" + filename;
            File f = new File(filename);
            if (f.exists())
                if(f.delete())
            try {
                if(f.createNewFile())
                    return new FileOutputStream(f);
            } catch (IOException e) {
                System.err.println("I/O error");
            }
        }
        return System.out;
    }
}


