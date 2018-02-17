package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CommandPrintWorkDirectory extends SingleCommand {
    private static final String COMMAND_NAME = "pwd";

    public CommandPrintWorkDirectory(Shell shell, Parser.CmdLineArgs args,
                                     InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public int run() {
        if (validValue() != 0)
            return 1;
        String info = getShell().getDir().getAbsolutePath() + "\n";
        if (writeTo(info) != 0)
            return 1;
        return 0;
    }

    private int validValue() {
        if (!getArgs().parameter.isEmpty()) {
            System.out.println("命令'" + getCommandName() + "'用法错误");
            return 1;
        }
        return 0;
    }

    private int writeTo(String info) {
        try {
            getOutput().write(info.getBytes());
        } catch (IOException e) {
            System.err.println("写入异常");
            return 1;
        }
        return 0;
    }
}
