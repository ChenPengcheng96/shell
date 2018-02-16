package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser.CmdLineArgs;

import java.io.File;
import java.io.IOException;
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
        // TODO: support  cd ..\ABC
        // current.getPath() + dir
        // create File (C:\\ABC\\..BCD"
        List<String> args = getArgs().parameter;
        if (args.size() > 1) {
            System.err.println("命令'" + getCommandName() + "'用法错误");
            return 1;
        }
        String workDir;
        try {
            workDir = getShell().getDir().getCanonicalPath();
        } catch (IOException e) {
            System.err.println("无规范文件查询系统");
            return 1;
        }

        if (!args.isEmpty()) {
            String arg = args.get(0);
            if (!arg.matches("[a-zA-Z]:.*?"))
                workDir += "\\" + arg;
            else
                workDir = arg;
        }
        File file = new File(workDir);

        if (!file.exists()) {
            System.err.println("文件不存在");
            return 1;
        }
        getShell().setDir(file);
        return 0;
    }
}