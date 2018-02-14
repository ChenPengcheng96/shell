package com.shell.command;

import java.io.*;
import java.util.List;

public class PipedCommand implements ICommand{
    private List<SingleCommand> commands;

    public PipedCommand(List<SingleCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void run() throws IOException {
        for (int i = 0; i < commands.size(); i++) {
            PipedOutputStream pos = new PipedOutputStream();
            commands.get(i).setOutput(pos);
            PipedInputStream pis = new PipedInputStream(pos);
            commands.get(++i).setInput(pis);
        }
        for(SingleCommand c:commands){
            c.run();
            c.destory();
        }
    }

    @Override
    public void destory() {}

    public static void main(String[] args) throws IOException {
        PipedOutputStream os = new PipedOutputStream();
        PipedInputStream is = new PipedInputStream(os);
        //os.connect(is);
        String s = "Hello";
        os.write(s.getBytes());
        byte[] b = new byte[100];
        is.read(b, 0, 100);
        System.out.println(new String(b));
    }
}
