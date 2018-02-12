package main;

import myCommand.CD;
import myCommand.Command;
import myCommand.LS;
import myCommand.PWD;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Shell {
    private static File dir = new File("");
    private static Map<String,? super Command> cmdMap= new HashMap<>();

    public static Map<String, ? super Command> getCmdMap() {
        return cmdMap;
    }

    private Shell() {
        //建立命令映射表
        cmdMap.put("cd",new CD());
        cmdMap.put("ls",new LS());
        cmdMap.put("pwd",new PWD());

    }
    public static File getDir() {
        return dir.getAbsoluteFile();
    }

    public static void setDir(File dir) {
        Shell.dir = dir.getAbsoluteFile();
    }

    private static void showPrompt(){
        System.out.print(dir.getAbsolutePath()+">>");
    }

    //1、等待用户输入命令，按回车执行
    //2、将命令解析
    //3、执行命令
    public static void main(String[] args) throws IOException {
        new Shell();
        while (true) {
            showPrompt();
            String s = new Receiver().getCmdLine();
            Command command = new Parser(s).parse();
            if(command != null)
                command.run();
        }
    }
}