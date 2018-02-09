package myCommand;

import main.Shell;
import java.io.File;

public class CD extends Command{
    public CD(){
        setCmd("cd");
    }
    public void run(){
        String[] paras = getParas();
        if(paras.length == 0)
            return;

        File dir = new File(paras[0]);
        if(paras[0].equals(".."))
            dir = Shell.getDir().getParentFile();
        if(dir.exists())
            Shell.setDir(dir);
        else
            System.out.println("系统找不到指定路径");
    }
}
