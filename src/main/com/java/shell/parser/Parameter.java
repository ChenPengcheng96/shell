package com.java.shell.parser;

public class Parameter implements IParam {
    private String value;

    Parameter(String value) {
        this.value = value;
    }

    public void register(Parser.CmdLineArgs args) {
        args.parameter.add(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
