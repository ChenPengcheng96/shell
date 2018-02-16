package com.java.shell;

import com.java.shell.command.ICommand;
import com.java.shell.parser.IParser;
import com.java.shell.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    private  File dir = new File("");
    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();
        IParser parser = new Parser(shell);
        while (true) {
            String line = shell.receive();
            ICommand command = parser.parse(line);
            if(command !=null) {
                try {
                    command.run();
                } finally {
                    command.destroy();
                }
            }
        }
    }

    private void showPrompt() {
        try {
            System.out.print(dir.getCanonicalPath() + ">>");
        } catch (IOException e) {
            System.err.println("无规范文件名查询系统");
        }
    }

    private String receive() throws IOException {
        showPrompt();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine().trim();
    }

    public File getDir() {
        return dir.getAbsoluteFile();
    }

    //public String getEnv(String key);
    //public String setEnv(String key, String value);
    // set command: set ABC 1
    // echo $ABC
    // set ABC C:\Temp, cd $ABC
    // external command: java PATH


    public void setDir(File dir) {
        this.dir = dir.getAbsoluteFile();
    }
}