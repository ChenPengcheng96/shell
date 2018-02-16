package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser.CmdLineArgs;

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
        File file = null;
        if(getArgs().directory.size()>1) {
            System.out.println("命令'" + getCommandName() + "'用法错误");
            return;
        }
        String s = null;
        if(!getArgs().directory.isEmpty()) {
            s = getArgs().directory.get(0);
            if (s.equals("."))
                s = getShell().getDir().getCanonicalPath();
            else if (s.equals(".."))
                s = getShell().getDir().getParent();
            else if (!s.matches("[a-zA-Z]:.*?"))
                s = getShell().getDir().getCanonicalPath() + "\\" + s;
        }
        file = new File(s==null?getShell().getDir().getAbsolutePath():s);
        if(!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        getShell().setDir(file.getCanonicalFile());
    }

}