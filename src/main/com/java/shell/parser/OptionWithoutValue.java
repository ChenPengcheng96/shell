package com.java.shell.parser;

public class OptionWithoutValue implements IParam {
    private String value;

    OptionWithoutValue(String value) {
        this.value = value;
    }

    @Override
    public void register(Parser.CmdLineArgs args) {
        args.optionWithoutValue.add(value);
    }
    public String toString(){
        return value;
    }
}
