package com.shell.command;

import com.shell.Shell;
import com.shell.parser.Parser.CmdLineArgs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommandList extends SingleCommand {
    private static final String COMMAND_NAME = "ls";

    public CommandList(Shell shell, CmdLineArgs args,
                       InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    //-a 当前文件夹所有文件（包括隐藏文件）
    //-l 当前文件夹详情文件信息
    public void run() throws IOException {
        CmdLineArgs paras = getArgs();
        File[] files = getShell().getDir().listFiles(pathname -> !pathname.isHidden());
        StringBuilder s = new StringBuilder();
        int count = 0;
        for (File file : files) {
            s.append(file.getName()).append("\t");
            count++;
            if (count % 7 == 0)
                s.append("\n");
        }
        if(!paras.directory.isEmpty()){
            System.out.println("命令'"+getCommandName()+"'用法错误");
            return;
        }

        for (int i = 0; i < paras.singleArg.size(); i++) {
            String p =  paras.singleArg.get(i);
            if (p.equals("-a"))
                files = getShell().getDir().listFiles();
            else if (p.equals("-l")) {
                s.delete(0,s.length());
                for (File file : files) {
                    String size = String.format("%-10d", file.length());
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm", Locale.ENGLISH);
                    String time = sdf.format(new Date(file.lastModified()));
                    s.append(size).append("\t").append(time).append("\t").append(file.getName()).append("\n");
                }
            }
            else{
                System.out.println("'"+p+"'"+"参数不存在");
                return;
            }
        }
        getOutput().write((s.toString() + "\n").getBytes());
    }
}
