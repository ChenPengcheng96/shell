package com.java.shell.command;

import java.io.IOException;

public interface ICommand {
    void run() throws IOException;
    void destory();
}
