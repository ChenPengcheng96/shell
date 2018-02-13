package com.shell.command;

import com.shell.Shell;
import com.shell.parser.Parser.CmdLineArgs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class CommandChangeDirectory extends SingleCommand {
    private static final String COMMAND_NAME = "cd";

    public CommandChangeDirectory(Shell shell, CmdLineArgs args,
                                  InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public void run() throws IOException {
//        getShell().getDir().getPath() args
        File file = null;
        for(String s:getArgs().singleArg){
            if(!s.startsWith("-"))
                if(s.equals("."))
                    s = getShell().getDir().getCanonicalPath();
                if(s.equals(".."))
                    s = getShell().getDir().getParent();
                if(s != null)
                    file = new File(s);
        }
        if (file == null)
            return;//没有指定文件夹则在当前文件夹
        getShell().setDir(file.getCanonicalFile());
    }

}
