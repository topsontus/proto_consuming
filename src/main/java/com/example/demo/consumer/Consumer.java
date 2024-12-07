package com.example.demo.consumer;

public interface Consumer<T> {
    void startConsuming();

    void consumeMessage(T message);
}
