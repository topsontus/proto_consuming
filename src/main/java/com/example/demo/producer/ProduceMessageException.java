package com.example.demo.producer;

public class ProduceMessageException extends Exception {
    public ProduceMessageException(Throwable e) {
        super("Failed to produce message", e);
    }
}
