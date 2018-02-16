package com.java.shell.command;

import java.io.*;
import java.util.List;

public class PipedCommand implements ICommand{
    private List<SingleCommand> commands;

    public PipedCommand(List<SingleCommand> commands) {
        this.commands = commands;
    }

    @Override
    public int run(){
        for (int i = 0; i < commands.size(); i++) {
            PipedOutputStream pos = new PipedOutputStream();
            commands.get(i).setOutput(pos);
            PipedInputStream pis = null;
            try {
                pis = new PipedInputStream(pos);
            } catch (IOException e) {
                System.err.println("管道连接异常");
            }
            commands.get(++i).setInput(pis);
        }
        for(SingleCommand c:commands){
            c.run();
            c.destroy();
        }
        return 0;
    }

    @Override
    public void destroy() {}

}
