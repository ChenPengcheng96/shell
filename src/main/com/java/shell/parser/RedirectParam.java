package com.java.shell.parser;

import com.sun.javafx.image.BytePixelSetter;

import java.util.Optional;

public class RedirectParam implements IParam {
    private RedirectType type;
    private String value;

    public RedirectParam(RedirectType type,String value) {
        this.type = type;
        this.value = value;

    }

    @Override
    public void register(Parser.CmdLineArgs args) {
        if(type.equals(RedirectType.Input))
            args.redirectInputArg = Optional.of(value);
        else
            args.redirectOutputArg = Optional.of(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public enum RedirectType {
        Input,
        Output
    }
}
