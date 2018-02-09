package main;

import myCommand.Command;

//命令解析器
//1、解析命令
public class Parser {


    public static Command parse(String cmdLine) {
        String[] cmdWords = cmdLine.split(" ");
        String cmd = cmdWords[0];
        String[] paras = new String[cmdWords.length-1];
        System.arraycopy(cmdWords,1,paras,0,cmdWords.length-1);
        Command command = (Command)Shell.getCmdMap().get(cmd);
        if(command == null){
            System.out.println("'"+cmd+"'"+"不是外部命令，也不是可运行的程序或批处理文件。");
            return null;
        }
        command.setParas(paras);
        return command;
    }
}
