package com.example.demo.model;

public interface RecordMapper<T, D> {
    D map(T record);
}
