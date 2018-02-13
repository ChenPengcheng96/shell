package myCommand;

import main.Shell;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LS extends Command {
    @Override
    public String getCommandName() {
        return "ls";
    }

    //-a 当前文件夹所有文件（包括隐藏文件）
    //-l 当前文件夹详情文件信息
    public void run(Shell shell) throws IOException {
        List<String> paras = getParas();
        if(paras.size() == 0)
            defaultRun(shell);
        else if(paras.contains("-a"))
            aRun(shell);
        else if(paras.contains("-l"))
            lRun(shell);

    }

    private void defaultRun(Shell shell) throws IOException {
        File[] files = shell.getDir().listFiles(pathname -> !pathname.isHidden());
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

    private void aRun(Shell shell) throws IOException {
        File[] files = shell.getDir().listFiles();
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

    private void lRun(Shell shell) throws IOException {
        File[] files = shell.getDir().listFiles();
        if (files != null) {
            int count = 0;
            for (File file : files) {
                String size = String.format("%-10d", file.length());
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
