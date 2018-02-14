package com.shell.parser;

import com.shell.command.ICommand;

import java.io.IOException;
import java.util.List;

public interface IParser {
    ICommand parse(String line) throws IOException;
}
