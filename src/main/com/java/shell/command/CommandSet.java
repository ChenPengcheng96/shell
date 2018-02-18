package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandSet extends SingleCommand{
    private static final String COMMAND_NAME = "set";

    public CommandSet(Shell shell, Parser.CmdLineArgs args, InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public int run() {
        Map<String,String> env = new HashMap<>();
        env.putAll(System.getenv());
        List<String> pair =  getArgs().parameter;
        //判断命令输入是否有效
        if(validValue(pair) != 0)
            return 1;
        OutputStream output = getOutput();
        //无参数：显示所有的环境变量
        if(pair.size() == 0){
            for (String key:env.keySet()){
                try {
                    output.write(key.getBytes());
                    output.write(" = ".getBytes());
                    output.write(env.get(key).getBytes());
                    output.write("\n".getBytes());
                } catch (IOException e) {
                    System.err.println("写入错误");
                    return 1;
                }
            }
        }
        //一个参数：删除此键值对
        else if(pair.size() == 1){
            String key = pair.get(0);
            String value = env.remove(key);
            try {
                output.write("删除 ".getBytes());
                output.write(key.getBytes());
                output.write(":".getBytes());
                output.write(value.getBytes());
                output.write("\n".getBytes());
            } catch (IOException e) {
                System.err.println("写入错误");
            }
        }
        //两个参数：设置环境变量
        else{
            String key = pair.get(0);
            String value = pair.get(1);
            env.put(key,value);
        }
        getShell().setEnv(env);
        return 0;
    }

    private int validValue(List<String> pair){
        if(pair.size()>2){
            System.err.println("命令用法错误");
            return 1;
        }
        if(pair.size() == 1){
            String key = pair.get(0);
            if(!getShell().getEnv().containsKey(key)){
                System.err.println("无此环境变量");
                return 1;
            }

        }
        return 0;
    }
}
