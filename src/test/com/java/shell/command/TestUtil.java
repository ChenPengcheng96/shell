package com.java.shell.command;

import com.java.shell.Shell;
import com.java.shell.parser.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TestUtil {
    private TestUtil(){}
    public static CommandChangeDirectory createCommandFromArgs(Shell shell, String SingleLine) {
        Parser p = new Parser(shell);
        Parser.CmdLineArgs args = p.parseArgs(new ArrayList<>(Arrays.asList(SingleLine.split("\\s+"))));
        String inputString = "";
        InputStream input = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        OutputStream output = new ByteArrayOutputStream();
        return new CommandChangeDirectory(shell, args, input, output);
    }

    public static void deleteFiles(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : Objects.requireNonNull(f.listFiles()))
                deleteFiles(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to delete file: " + f);
    }

    public static void createFiles(File directory) throws IOException {
        if(!directory.exists())
            directory.mkdir();
        if(!directory.isDirectory())
            throw new RuntimeException(directory + " not a parameter");
        File dir = new File(directory + "\\dir1\\dir2");
        dir.mkdirs();
        new File(dir,"file.txt").createNewFile();
        File dirHidden = new File(directory+"\\dir1Hidden\\dir2Hidden");
        dirHidden.mkdirs();
        new File(dirHidden,"fileHidden.txt").createNewFile();
        Files.setAttribute(Paths.get(dirHidden.getParentFile().getAbsolutePath()), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
//        Runtime.getRuntime().exec("cmd.exe attrib +h "+dirHidden.getAbsolutePath());
    }

}
