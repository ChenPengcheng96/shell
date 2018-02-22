package com.java.shell;

import com.java.shell.command.ICommand;
import com.java.shell.parser.IParser;
import com.java.shell.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

public class Shell {
    private File dir = new File("");
    private Map<String, String> env;

    public Shell() {
        env = System.getenv();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(Charset.defaultCharset());
        Shell shell = new Shell();
        IParser parser = new Parser(shell);
        while (true) {
            String line = shell.receive();
            ICommand command = parser.parse(line);
            if (command != null) {
                try {
                    command.run();
                } finally {
                    command.destroy();
                }
            }
        }
    }

    private void showPrompt() {
        System.out.print(getDir());
        System.out.print(">>");
    }

    private String receive() throws IOException {
        showPrompt();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine().trim();
    }

    public Map<String, String> getEnv() {
        return env;
    }

    public void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public File getDir() {
        try {
            return dir.getCanonicalFile();
        } catch (IOException e) {

            System.err.println("无规范文件名查询系统");
        }
        return null;
    }

    //public String getEnv(String key);
    //public String setEnv(String key, String value);
    // set command: set ABC 1
    // echo $ABC
    // echo abc
    // set ABC C:\Temp, cd $ABC
    // external command: java PATH

    public void setDir(File dir) {
        this.dir = dir.getAbsoluteFile();
    }
}