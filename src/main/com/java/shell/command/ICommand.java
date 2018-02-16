package com.java.shell.command;

import java.io.IOException;

public interface ICommand {
    // TODO: no exception
    // TODO: return exit number
    int run();
    void destroy();
}
