package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.IParam;
import com.java.shell.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CommandEcho extends SingleCommand {
    private static final String COMMAND_NAME = "echo";
    public CommandEcho(Shell shell, Parser.CmdLineArgs args, InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public int run() {
        boolean tag_n = false;
        OutputStream output = getOutput();
        for(IParam p : getArgs().param){
            String info = p.toString();
            if(info.equals("-n")){
                tag_n = true;
                continue;
            }

            try{
                output.write(info.getBytes());
                output.write(" ".getBytes());
            }catch (Exception e){
                System.err.println("写入失败");
                return 1;
            }
        }
        try {
            if(!tag_n)
            output.write("\n".getBytes());
        } catch (IOException e) {
            System.err.println("写入失败");
            return 1;
        }
        return 0;
    }
}
