package com.shell.command;

import com.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CommandPrintWorkDirectory extends SingleCommand {
    public static final String COMMAND_NAME = "pwd";

    public CommandPrintWorkDirectory(Shell shell, List<String> args,
                                     InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public void run() throws IOException {
        String info = getShell().getDir().getAbsolutePath() + "\n";
        getOutput().write(info.getBytes());
    }
}
