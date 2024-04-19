package com.webandit.webuild.services;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceChantier implements CRUD<Chantier> {
    public static Connection cnx;
    public ServiceChantier() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Chantier chantier) throws SQLException {
        String req = "INSERT INTO `chantier`(`nom`, `description`, `remuneration`,`start_date`,`id`) VALUES " +
                "(?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, chantier.getNom());
        ps.setString(2, chantier.getDescription());
        ps.setFloat(3, chantier.getRemuneration());
        ps.setString(4, chantier.getDate());
        ps.setInt(5, chantier.getId());
        System.out.println("Chantier Added !");
        ps.executeUpdate(req);
    }

    @Override
    public void updateOne(Chantier chantier) throws SQLException {

    }

    @Override
    public void deleteOne(Chantier chantier) throws SQLException {

    }

    @Override
    public List<Chantier> selectAll() throws SQLException {
        return null;
    }
}
