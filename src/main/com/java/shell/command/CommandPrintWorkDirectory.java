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

    public void run() throws IOException {
        if(!getArgs().directory.isEmpty()) {
            System.out.println("命令'" + getCommandName() + "'用法错误");
            return;
        }
        String info = getShell().getDir().getAbsolutePath() + "\n";
        getOutput().write(info.getBytes());
    }
}
