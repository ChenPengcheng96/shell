package com.java.shell.parser;

public class OptionWithValue implements IParam {
    private String key;
    private String value;

    OptionWithValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void register(Parser.CmdLineArgs args) {
        args.optionWithValue.put(key, value);
    }

    public String toString() {
        return key + " " + value;
    }
}
