package com.example.thread;

import java.util.concurrent.Callable;

public class InsertTargetCallable implements Callable {
    @Override
    public Object call() throws Exception {

        return "hello";
    }
}
