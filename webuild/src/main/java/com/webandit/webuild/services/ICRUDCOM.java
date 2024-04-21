package com.webandit.webuild.services;

import com.webandit.webuild.models.Commentaire;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDCOM {
    void insertOne(Commentaire commentaire) throws SQLException;

    void updateOne(Commentaire commentaire) throws SQLException;

    void deleteOne(Commentaire commentaire) throws SQLException, SQLException;

    List<Commentaire> selectAll() throws SQLException;
}
