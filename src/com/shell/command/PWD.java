package com.shell.command;

import com.shell.Shell;

import java.io.IOException;

public class PWD extends Command {
    public String getCommandName(){
        return "pwd";
    }

    public void run(Shell shell) throws IOException {
        String info = shell.getDir().getAbsolutePath();
        println(info);
    }
}
