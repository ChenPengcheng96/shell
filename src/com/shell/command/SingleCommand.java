package com.shell.command;

import com.shell.Shell;
import com.shell.parser.Parser.CmdLineArgs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class SingleCommand implements ICommand {
    private Shell shell;
    private CmdLineArgs args;
    private InputStream input;
    private OutputStream output;

    public SingleCommand(Shell shell, CmdLineArgs args,
                         InputStream input, OutputStream output) {
        this.shell = shell;
        this.args = args;
        this.input = input;
        this.output = output;
    }

    public abstract String getCommandName();

    @Override
    public abstract void run() throws IOException;

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public void setArgs(CmdLineArgs args) {
        this.args = args;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public Shell getShell() {
        return shell;
    }

    public CmdLineArgs getArgs() {
        return args;
    }

    public OutputStream getOutput() {
        return output;
    }

}
