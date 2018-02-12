package myCommand;
import main.Shell;

import java.io.IOException;

public class PWD extends Command{
    public void run() throws IOException {
        String info = Shell.getDir().getAbsolutePath();
        outPut(info);
    }
}
