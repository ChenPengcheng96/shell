package com.java.shell.parser;

import com.java.shell.command.ICommand;

public interface IParser {
    ICommand parse(String line);
}
