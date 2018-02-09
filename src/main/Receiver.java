package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//命令接受器
public class Receiver {
    private String cmdLine;

    public String getCmdLine() {
        return cmdLine;
    }

    Receiver() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        cmdLine = br.readLine().trim();
    }
}
