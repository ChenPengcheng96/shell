package myCommand;
import main.Shell;

public class PWD extends Command{
    public PWD(){
        setCmd("pwd");
    }

    public void run(){
        System.out.println("\\"+Shell.getDir().getName());
    }
}
