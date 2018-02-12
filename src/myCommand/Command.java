package myCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class Command {
    private static final String SEPARATOR = System.lineSeparator();
    private String cmd;
    private List<String> paras;
    private File rdFile;//重定向文件夹
    private int mode = 0;

    public abstract void run() throws IOException;

    public File getRdFile() {
        return rdFile;
    }

    public void setRdFile(File rdFile) {
        this.rdFile = rdFile;
    }

    void println(String info) throws IOException {
        if (mode == 0)
            System.out.println(info);
        if (mode == 1 || mode == 2) {
            FileWriter fw = new FileWriter(rdFile, true);
            fw.write(info+SEPARATOR);
            fw.close();
        }
    }

    void print(String info) throws IOException {
        if (mode == 0)
            System.out.print(info);
        if (mode == 1 || mode == 2) {
            FileWriter fw = new FileWriter(rdFile, true);
            fw.write(info);
            fw.close();
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getCmd() {
        return cmd;

    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<String> getParas() {
        return paras;
    }

    public void setParas(List<String> paras) {
        this.paras = paras;
    }
}
