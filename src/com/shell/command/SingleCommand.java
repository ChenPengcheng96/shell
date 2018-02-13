package com.shell.command;

import com.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class SingleCommand implements ICommand {
    private Shell shell;
    private List<String> args;
    private InputStream input;
    private OutputStream output;

    public SingleCommand(Shell shell, List<String> args,
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

    public void setArgs(List<String> args) {
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

    public List<String> getArgs() {
        return args;
    }

    public OutputStream getOutput() {
        return output;
    }

}
