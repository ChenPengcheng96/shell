package com.shell.command;

import com.shell.Shell;
import com.shell.parser.Parser.CmdLineArgs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class SingleCommand implements ICommand {
    private Shell shell;
    private CmdLineArgs args;
    private InputStream input;
    private OutputStream output;

    SingleCommand(Shell shell, CmdLineArgs args,
                  InputStream input, OutputStream output) {
        this.shell = shell;
        this.args = args;
        this.input = input;
        this.output = output;
    }

    public abstract String getCommandName();

    @Override
    public abstract void run() throws IOException;

    public void destory() {
        if(!output.equals(System.out)) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!input.equals(System.in))
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    InputStream getInput() {
        return input;
    }

    public Shell getShell() {
        return shell;
    }

    CmdLineArgs getArgs() {
        return args;
    }

    OutputStream getOutput() {
        return output;
    }
    public void setOutput(OutputStream output) {
        this.output = output;
    }

}
