package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.Closeable;
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
        close(input);
        close(output);
    }

    private void close(Closeable stream) {
        if (stream == null)
            return;
        if (!stream.equals(System.out) && !stream.equals(System.in)) {
            try {
                stream.close();
            } catch (IOException e) {
                System.err.println("流关闭失败");
            }
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
