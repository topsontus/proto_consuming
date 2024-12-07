package com.example.demo.repository;

import java.sql.SQLException;

public interface Repository<T extends Record> {
    int create(T record) throws SQLException;
}

