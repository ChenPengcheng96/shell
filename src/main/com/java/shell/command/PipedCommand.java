package com.java.shell.command;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public class PipedCommand implements ICommand {
    private List<SingleCommand> commands;

    public PipedCommand(List<SingleCommand> commands) {
        this.commands = commands;
    }

    @Override
    public int run() {
        if (connectWithPipe() != 0)
            return 1;
        runSingleCommand();
        return 0;
    }

    private int connectWithPipe() {
        for (int i = 0; i < commands.size(); i++) {
            PipedOutputStream pos = new PipedOutputStream();
            commands.get(i).setOutput(pos);
            PipedInputStream pis;
            try {
                pis = new PipedInputStream(pos);
            } catch (IOException e) {
                System.err.println("管道连接异常");
                return 1;
            }
            commands.get(++i).setInput(pis);
        }
        return 0;
    }

    private void runSingleCommand() {
        for (SingleCommand c : commands) {
            c.run();
            c.destroy();
        }
    }

    @Override
    public void destroy() {
    }

}
