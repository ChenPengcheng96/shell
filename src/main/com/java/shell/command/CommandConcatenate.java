package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.*;
import java.util.List;

public class CommandConcatenate extends SingleCommand{
    private static final String COMMAND_NAME = "cat";
    public CommandConcatenate(Shell shell, Parser.CmdLineArgs args,
                              InputStream input, OutputStream output){
        super(shell,args,input,output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void run() throws IOException {
        List<String> filenames = getArgs().directory;
        if(filenames.size() == 1){
            File file = new File(filenames.get(0));
            setInput(new FileInputStream(file));
        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(getInput()));
        OutputStream output = getOutput();
        String s;
        while((s = bf.readLine())!=null){
            output.write((s+"\n").getBytes());
            if(s.trim().equals("") )
                break;
        }
    }
}
