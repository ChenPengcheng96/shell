package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CommandExternal extends SingleCommand{
    String COMMAND_NAME;

    public CommandExternal(String commandName,Shell shell, Parser.CmdLineArgs args, InputStream input, OutputStream output) {
        super(shell, args, input, output);
        COMMAND_NAME = commandName;

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public int run() {
        String exePath = getCommandName()+".exe";
        String pathString = System.getenv().get("Path");
        List<String> pathList = Arrays.asList(pathString.split(";"));
        for(String path:pathList){
            File filepath = new File(path);
            String[] filenameList = filepath.list();
            if(filenameList==null)
                continue;
            for(String filename: filenameList){
                if(filename.equals(exePath))
                    try {
                        exePath = filepath.getAbsolutePath()+"\\"+exePath;
                        ProcessBuilder pb = new ProcessBuilder(exePath);
                        pb.redirectErrorStream(true);
                        Process p = pb.start();
                        InputStream is = p.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is,"GBK");
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = br.readLine()) != null) {
                            getOutput().write(line.getBytes());
                            getOutput().write("\n".getBytes());
                        }
                        break;
                    } catch (IOException e) {
                        System.err.println("程序运行失败");
                        return 1;
                    }
            }
            break;
        }
        return 0;
    }
}
