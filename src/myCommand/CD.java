package myCommand;

import main.Parser;
import main.Shell;
import java.io.File;
import java.util.List;

public class CD extends Command{
    public void run(){
        List<File> fileList=  Parser.parseDir();
        if(fileList.size() == 0)
            return;//没有指定文件夹则在当前文件夹
        File dir = fileList.get(0);
        Shell.setDir(dir);
    }
}
