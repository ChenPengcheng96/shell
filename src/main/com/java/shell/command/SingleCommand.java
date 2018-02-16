package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class SingleCommand implements ICommand {
    private Shell shell;
    private Parser.CmdLineArgs args;
    private InputStream input;
    private OutputStream output;

    SingleCommand(Shell shell, Parser.CmdLineArgs args,
                  InputStream input, OutputStream output) {
        this.shell = shell;
        this.args = args;
        this.input = input;
        this.output = output;
    }

    public abstract String getCommandName();

    @Override
    public abstract int run();

    public void destroy() {
        // TODO: extract these to a function
        // close(input);
        // close(output);
        if(output == null)
            return;
        if(!output.equals(System.out)) {
            try {
                output.close();
            } catch (IOException e) {
                System.err.println("流关闭失败");
            }
        }
        if(input == null)
            return;
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

    Parser.CmdLineArgs getArgs() {
        return args;
    }

    OutputStream getOutput() {
        return output;
    }
    public void setOutput(OutputStream output) {
        this.output = output;
    }

}
