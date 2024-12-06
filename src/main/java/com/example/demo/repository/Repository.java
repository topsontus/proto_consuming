package com.example.demo.repository;

public interface Repository<T extends Record> {
    int create(T record);
}

