package com.webandit.webuild.services;

import com.webandit.webuild.models.Offre;
import com.webandit.webuild.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffre implements CRUD<Offre> {

    private Connection cnx;

    public ServiceOffre() {
        cnx = DBConnection.getInstance().getCnx();
    }


    public void insertOne(Offre offre) throws SQLException {
        String req = "INSERT INTO `offre`(`title`, `description`, `salary`, `latitude`, `longitude`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, offre.getTitle());
        ps.setString(2, offre.getDescription());
        ps.setFloat(3, offre.getSalary());
        ps.setBigDecimal(4, offre.getLatitude());
        ps.setBigDecimal(5, offre.getLongitude());

        // Execute the prepared statement
        ps.executeUpdate();
        System.out.println("Offre Added !");
    }


    @Override
    public void updateOne(Offre offre) throws SQLException {
        String req = "UPDATE `offre` SET `title`=?, `description`=?, `salary`=?, `latitude`=?, `longitude`=? WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, offre.getTitle());
        ps.setString(2, offre.getDescription());
        ps.setFloat(3, offre.getSalary());
        ps.setBigDecimal(4, offre.getLatitude());
        ps.setBigDecimal(5, offre.getLongitude());
        ps.setInt(6, offre.getId());

        ps.executeUpdate();
        System.out.println("Offre Updated !");
    }


    @Override
    public void deleteOne(Offre offre) throws SQLException {

        String req = "DELETE FROM `offre` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, offre.getId());
        ps.executeUpdate();
        System.out.println("Offre Deleted !");
    }

    @Override
    public List<Offre> selectAll() throws SQLException {
        List<Offre> offreList = new ArrayList<>();

        String req = "SELECT * FROM `offre`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Offre o = new Offre();

            o.setId(rs.getInt("id"));
            o.setTitle(rs.getString("title"));
            o.setDescription(rs.getString("description"));
            o.setSalary(rs.getFloat("salary"));
            o.setLatitude(rs.getBigDecimal("latitude"));
            o.setLongitude(rs.getBigDecimal("longitude"));

            offreList.add(o);
        }

        return offreList;
    }

    //retrieving offre based on their id
    public Offre getOffreById(int id) throws SQLException {
        String req = "SELECT * FROM `offre` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Offre offre = new Offre();
            offre.setId(rs.getInt("id"));
            offre.setTitle(rs.getString("title"));
            offre.setDescription(rs.getString("description"));
            offre.setSalary(rs.getFloat("salary"));
            offre.setLatitude(rs.getBigDecimal("latitude"));
            offre.setLongitude(rs.getBigDecimal("longitude"));
            return offre;
        }

        // Return null if no Offre with the given ID is found
        return null;
    }
    public Offre getOffreByTitle(String title ) throws SQLException {
        String req = "SELECT * FROM `offre` WHERE `title`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, title);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Offre offre = new Offre();
            offre.setId(rs.getInt("id"));
            offre.setTitle(rs.getString("title"));
            offre.setDescription(rs.getString("description"));
            offre.setSalary(rs.getFloat("salary"));
            offre.setLatitude(rs.getBigDecimal("latitude"));
            offre.setLongitude(rs.getBigDecimal("longitude"));
            return offre;
        }

        // Return null if no Offre with the given title is found
        return null;
    }
}
