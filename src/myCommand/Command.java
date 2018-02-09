package myCommand;

public abstract class Command {
    private String cmd;
    private String[] paras;

    public abstract void run();

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String[] getParas() {
        return paras;
    }

    public void setParas(String[] paras) {
        this.paras = paras;
    }
}
