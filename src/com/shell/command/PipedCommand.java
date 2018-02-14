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
        if(commands.size() == 2){
            SingleCommand c0 = commands.get(0);
            PipedOutputStream pos0 = new PipedOutputStream();
            c0.setOutput(pos0);
            SingleCommand c1 = commands.get(1);
            PipedInputStream pis1 = new PipedInputStream(pos0);
            c1.setInput(pis1);
        }
        else if(commands.size()>2){
            for (int i = 0; i < commands.size(); i++) {
                PipedOutputStream pos = new PipedOutputStream();
                commands.get(i).setOutput(pos);
                PipedInputStream pis = new PipedInputStream(pos);
                commands.get(i+1).setInput(pis);
            }
        }
        for(SingleCommand c:commands){
            c.run();
            c.destory();
        }
    }

    @Override
    public void destory() {
//        for(SingleCommand c:commands){
//            c.destory();
//        }
    }

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
