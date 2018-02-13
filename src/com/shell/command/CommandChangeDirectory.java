package com.shell.command;

import com.shell.parser.Parser;
import com.shell.Shell;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CommandChangeDirectory extends SingleCommand {
    private static final String COMMAND_NAME = "cd";

    public CommandChangeDirectory(Shell shell, List<String> args,
                                  InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public void run() throws IOException {
//        getShell().getDir().getPath() args
        List<File> fileList = Parser.parseDirectory();
        if (fileList.size() == 0)
            return;//没有指定文件夹则在当前文件夹
        File dir = fileList.get(0).getCanonicalFile();
        getShell().setDir(dir);
    }

}
