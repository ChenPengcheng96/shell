package myCommand;

import main.Shell;

import java.io.File;

public class LS extends Command{
    public LS(){
        setCmd("ls");
    }
    public void run(){
        File[] files= Shell.getDir().listFiles();
        if (files != null) {
            int count = 0;
            for (File file : files) {
                System.out.print(file.getName() + "\t");
                count++;
                if(count%7 == 0)
                    System.out.println();
            }
            System.out.println();
        }
    }
}
