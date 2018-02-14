package com.shell.command;

import java.io.IOException;

public interface ICommand {
    void run() throws IOException;
    String getCommandName();
    void destory();
}
