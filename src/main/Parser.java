package main;

import myCommand.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//命令解析器
//1、解析命令
public class Parser {
    private static Command command;//用于解析器返回的命令对象
    private static List<String> wordList;//命令行字符串数组
    Parser(String cmdLine){
        String[] words = cmdLine.split(" ");
        wordList = new ArrayList<String>(Arrays.asList(words));
        }

    public Command parse() throws IOException {//解析
        parseCmd();
        parseMode();
        parseParas();
        return command;
    }
    private void parseCmd(){//解析命令
        String cmd = wordList.remove(0);
        command = (Command)Shell.getCmdMap().get(cmd);//获取具体命令实例对象
        if(command == null) //检查有无此命令
            System.out.println("'" + cmd + "'" + "不是外部命令，也不是可运行的程序或批处理文件。");
        command.setCmd(cmd);
    }

    private void parseParas(){//解析参数
        List<String> parasList = new ArrayList<String>();
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if(s.startsWith("-")) {
                parasList.add(s);
                parasList.remove(i);
            }
        }
        command.setParas(parasList);
    }

    private void parseMode() throws IOException {//解析输出模式
        //设置输出设备，默认为0---控制台
        for (int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            if(s.equals(">")) {
                command.setMode(1);
                File f = new File(wordList.get(i + 1));
                if (f.exists())
                    f.delete();
                f.createNewFile();
                command.setRdFile(f);
                wordList.remove(i);
                wordList.remove(i+1);
            }
            else if(s.equals(">>")){
                command.setMode(1);
                File f = new File(wordList.get(i + 1));
                if(!f.exists())
                    f.createNewFile();
                command.setRdFile(f);
                wordList.remove(i);
                wordList.remove(i+1);
            }
        }
    }
     public static List<File> parseDir(){//解析文件夹（工具命令）
        List<File> fileList = new ArrayList<>();
        File f;
        for (int i = 0; i < wordList.size(); i++) {
            String filename = wordList.get(i);
            f = new File(filename);
            if(f.exists()) {
                fileList.add(f);
                wordList.remove(i);
            }
        }
        return fileList;
    }
}
