package com.shell.command;

import com.shell.parser.Parser;
import com.shell.Shell;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CD extends Command {
    @Override
    public String getCommandName() {
        return "cd";
    }
    private static final String COMMAND_NAME = "cd";
    public void run(Shell shell) throws IOException {
        List<File> fileList = Parser.parseDir();
        if (fileList.size() == 0)
            return;//没有指定文件夹则在当前文件夹
        File dir = fileList.get(0).getCanonicalFile();
        shell.setDir(dir);
    }


}
