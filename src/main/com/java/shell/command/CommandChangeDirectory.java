package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser.CmdLineArgs;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CommandChangeDirectory extends SingleCommand {
    private static final String COMMAND_NAME = "cd";

    public CommandChangeDirectory(Shell shell, CmdLineArgs args,
                                  InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    public int run() {
        List<String> args = getArgs().parameter;
        if(validValue(args) != 0)
            return 1;
        String workDir;
        if(!args.isEmpty()){
            File file;
            workDir = args.get(0);
            //判断是否为绝对路径
            if (!workDir.matches("[a-zA-Z]:.*?"))
                file = new File(getShell().getDir(),workDir);
            else
                file = new File(workDir);
            getShell().setDir(file);
        }
        return 0;
    }

    private int validValue(List<String> args){
        if (args.size() > 1) {
            System.err.println("命令'" + getCommandName() + "'用法错误");
            return 1;
        }
        if(!args.isEmpty()){
            String filename = args.get(0);
            if (!new File(filename).exists()) {
                System.err.println("文件不存在");
                return 1;
            }
        }
        return 0;
    }
}