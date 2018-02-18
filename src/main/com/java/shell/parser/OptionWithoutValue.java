package com.java.shell.parser;

public class OptionWithoutValue implements IParam {
    private String value;

    OptionWithoutValue(String value) {
        this.value = value;
    }

    @Override
    public void register(Parser.CmdLineArgs args) {
        char[] chars = value.toCharArray();
        for (int j = 1; j < chars.length; j++)
            args.optionWithoutValue.add("-" + chars[j]);
    }

    public String toString() {
        return value;
    }
}
