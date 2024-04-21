package com.webandit.webuild.services;

import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.models.Location;
import com.webandit.webuild.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceLocation implements CRUD<Location> {

    public static Connection cnx;

    public ServiceLocation() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Location location) throws SQLException {
        String query = "INSERT INTO location (pack_materiel_id, date_d, date_f,id_user) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, location.getM().getId());
        pstmt.setDate(2, location.getDate_d());
        pstmt.setDate(3, location.getDate_f());
        pstmt.setInt(4, location.getId_user()); // Assuming getId() returns the ID of the Materiel
        pstmt.executeUpdate();
    }

    @Override
    public void updateOne(Location location) throws SQLException {
        String query = "UPDATE location SET pack_materiel_id = ?, date_d = ?, date_f = ?, d_user = ?  WHERE id_l = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, location.getM().getId());
        pstmt.setDate(2, location.getDate_d());
        pstmt.setDate(3, location.getDate_f());
        pstmt.setInt(4, location.getId_user());
        pstmt.setInt(5, location.getId_l());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteOne(Location location) throws SQLException {
        String query = "DELETE FROM location WHERE id_l = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, location.getId_l());
        pstmt.executeUpdate();
    }

    @Override
    public List<Location> selectAll() throws SQLException {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM location";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Location location = new Location();
            location.setId_l(rs.getInt("id_l"));
            location.setId_user(rs.getInt("id_user"));
            location.setDate_d(rs.getDate("date_d"));
            location.setDate_f(rs.getDate("date_f"));
            // Assuming you have a ServiceMateriel class to fetch Materiel details
            ServiceMateriel serviceMateriel = new ServiceMateriel();
            Materiel materiel = serviceMateriel.selectOne(rs.getInt("pack_materiel_id"));
            location.setM(materiel);
            locations.add(location);
        }
        return locations;
    }
}
