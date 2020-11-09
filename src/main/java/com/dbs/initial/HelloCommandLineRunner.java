package com.dbs.initial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HelloCommandLineRunner implements CommandLineRunner {
    //7
    @Override
    public void run(String... args) throws Exception {
        System.out.println("HelloCommandLineRunner...run..." + Arrays.asList(args));
    }
}
