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
    private  File dir = new File("");
    static private  Map<String, ? super Command> cmdMap = new HashMap<>();

    public static Map<String, ? super Command> getCmdMap() {
        return cmdMap;
    }

    protected void addCommand(Command c) {
        cmdMap.put(c.getCommandName(),c);
    }

    private Shell() {
        addCommand(new CD());
        addCommand(new LS());
        addCommand(new PWD());
    }

    public File getDir() {
        return dir.getAbsoluteFile();
    }

    public void setDir(File dir) {
        this.dir = dir.getAbsoluteFile();
    }

    private  void showPrompt() {
        System.out.print(dir.getAbsolutePath() + ">>");
    }

    //1、等待用户输入命令，按回车执行
    //2、将命令解析
    //3、执行命令
    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();
        while (true) {
            shell.showPrompt();
            String s = new Receiver().getCmdLine();
            Command command = new Parser(s).parse();
            if (command != null)
                command.run(shell);
        }
    }
}