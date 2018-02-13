package com.shell.parser;

import com.shell.command.ICommand;

import java.io.IOException;

public interface IParser {
    ICommand parse(String line) throws IOException;
}
