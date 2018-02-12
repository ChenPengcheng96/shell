package myCommand;

import main.Shell;

import java.io.File;
import java.io.IOException;

public class LS extends Command{
    //-a 当前文件夹所有文件（包括隐藏文件）
    //-l 当前文件夹详情文件信息
    public void run() throws IOException {
        File[] files= Shell.getDir().listFiles();
        if (files != null) {
            int count = 0;
            for (File file : files) {
                outPut(file.getName() + "\t");
                count++;
                if(count%7 == 0)
                    outPut("\n");
            }
            System.out.println();
        }
    }

}
