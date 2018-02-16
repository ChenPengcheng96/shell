package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CommandList extends SingleCommand {
    private static final String COMMAND_NAME = "ls";

    public CommandList(Shell shell, Parser.CmdLineArgs args,
                       InputStream input, OutputStream output) {
        super(shell, args, input, output);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    //-a 当前文件夹所有文件（包括隐藏文件）
    //-l 当前文件夹详情文件信息
    public int run() {
        //设置显示目录
        List<String> paras = getArgs().parameter;
        File workDir = getShell().getDir();
        if (paras.size() == 1){
            workDir = new File(paras.get(0));
            if(!workDir.exists()) {
                System.err.println("文件不存在");
                return 1;
            }
        }
        else if(paras.size() > 1)
            System.err.println("命令'" + getCommandName() + "'用法错误");
        // pathname-> true
        //参数-a
        List<String> optionWithoutValue = getArgs().optionWithoutValue;
        File[] files;
        if(optionWithoutValue.contains("-a"))
            files = workDir.listFiles();
        else
            files = workDir.listFiles(pathname -> !pathname.isHidden());

        //参数-l
        if(files == null){
            return 1;
        }
        OutputStream output = getOutput();
        if(optionWithoutValue.contains("-l")){
            for (File file : files) {
                byte[] size = String.format("%-10d", file.length()).getBytes();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm", Locale.ENGLISH);
                byte[] time = sdf.format(new Date(file.lastModified())).getBytes();
                byte[] filename = file.getName().getBytes();
                try {
                    output.write(size);
                    output.write(time);
                    output.write("\t".getBytes());
                    output.write(filename);
                    output.write("\n".getBytes());
                } catch (IOException e) {
                    System.err.println("I/O error");
                }
            }
        }
        //无参数-l
        else{
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            int count = (int)width/400;
            for (File file : files) {
                // TODO: fit screen width
                try {
                    output.write(file.getName().getBytes());
                    output.write("\t".getBytes());
                    if (count-- == 0){
                        output.write("\n".getBytes());
                        count = (int)width/400;
                    }
                } catch (IOException e) {
                    System.err.println("I/O error");
                }
            }
        }

        //设置无值参数选项
        Set<String> option = new HashSet<>();
        option.add("-a");
        option.add("-l");
        //判断有无错误无值参数
        if(optionWithoutValue.retainAll(option)){
            System.err.println("参数错误");
            return 1;
        }
//        TODO: avoid +
        return 0;
    }

}
