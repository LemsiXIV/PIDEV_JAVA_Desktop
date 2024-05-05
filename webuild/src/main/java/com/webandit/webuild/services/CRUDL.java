package com.webandit.webuild.services;

import java.sql.SQLException;
import java.util.List;

public interface CRUDL<T> {
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(int t) throws SQLException;
    List<T> selectAll() throws SQLException;
    List<T> selectListDerou() throws SQLException;
}
