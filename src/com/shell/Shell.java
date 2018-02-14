package com.shell;

import com.shell.command.ICommand;
import com.shell.parser.IParser;
import com.shell.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    private  File dir = new File("");
    private static ICommand command;

    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();
        IParser parser = new Parser(shell);
        while (true) {
            String line = shell.receive();
            command = parser.parse(line);
            if(command!=null) {
                try {
                    command.run();
                } finally {
                    command.destory();
                }
            }

        }
    }

    private void showPrompt() {
        System.out.print(dir.getAbsolutePath() + ">>");
    }

    private String receive() throws IOException {
        showPrompt();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine().trim();
    }

    public File getDir() {
        return dir.getAbsoluteFile();
    }

    public void setDir(File dir) {
        this.dir = dir.getAbsoluteFile();
    }
}