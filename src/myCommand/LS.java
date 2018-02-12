package myCommand;

import main.Shell;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LS extends Command {
    //-a 当前文件夹所有文件（包括隐藏文件）
    //-l 当前文件夹详情文件信息
    public void run() throws IOException {
        List<String> paras = getParas();
        if(paras.size() == 0)
            defaultRun();
        else if(paras.contains("-a"))
            aRun();
        else if(paras.contains("-l"))
            lRun();

    }

    private void defaultRun() throws IOException {
        File[] files = Shell.getDir().listFiles(pathname -> !pathname.isHidden());
        if (files != null) {
            int count = 0;
            for (File file : files) {
                print(file.getName() + "\t");
                count++;
                if (count % 7 == 0)
                    print("\n");
            }
            System.out.println();
        }
    }

    private void aRun() throws IOException {
        File[] files = Shell.getDir().listFiles();
        if (files != null) {
            int count = 0;
            for (File file : files) {
                print(file.getName() + "\t");
                count++;
                if (count % 7 == 0)
                    print("\n");
            }
            System.out.println();
        }
    }

    private void lRun() throws IOException {
        File[] files = Shell.getDir().listFiles();
        if (files != null) {
            int count = 0;
            for (File file : files) {
                long size = file.length();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm", Locale.ENGLISH);
                String time = sdf.format(new Date(file.lastModified()));
                println(size+"\t"+time+"\t"+file.getName());
                count++;
                if (count % 7 == 0)
                    print("\n");
            }
            System.out.println();
        }
    }
}
