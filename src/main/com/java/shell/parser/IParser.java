package com.java.shell.parser;

import com.java.shell.command.ICommand;

import java.io.IOException;

public interface IParser {
    ICommand parse(String line);
}
