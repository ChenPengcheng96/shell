package com.shell.command;

import java.io.*;
import java.util.List;

public class PipedCommand implements ICommand{
    private List<SingleCommand> commands;

    @Override
    public void run() throws IOException {

    }

    @Override
    public void destory() {

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
