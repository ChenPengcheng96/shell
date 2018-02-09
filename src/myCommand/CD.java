package myCommand;

import main.Shell;
import java.io.File;

public class CD extends Command{
    public CD(){
        setCmd("cd");
    }
    public void run(){
        File dir = new File(getParas()[0]);
        if(dir.exists())
            Shell.setDir(dir);
        else
            System.out.println("系统找不到指定路径");
    }
}
