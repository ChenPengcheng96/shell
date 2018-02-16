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
    public int run(){
        List<String> filenames = getArgs().parameter;
        // TODO: support multiple file
        if(filenames.size()>0){
            for (int i = 0; i < filenames.size(); i++) {
                String filename = filenames.get(i);
                copyToInputStream(filename);
                pasteToOutputStream();
            }
        }
        else
            pasteToOutputStream();
        return 0;
    }

    private void copyToInputStream(String filename){//将文件中内容拷贝到cat的输入流中
        File file;
        file = new File(filename);
        try {
            setInput(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.err.println("文件不存在");
        }
    }

    private void pasteToOutputStream(){
        BufferedReader bf = new BufferedReader(new InputStreamReader(getInput()));
        OutputStream output = getOutput();
        String s = null;
        do {
            try {
                s = bf.readLine();
                output.write(s.getBytes());
                output.write("\n".getBytes());
                if(s.trim().equals("") )
                    break;
            } catch (IOException e) {
                System.err.println("文件不存在");
            }
        }while (s != null);
    }
}
