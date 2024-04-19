package com.webandit.webuild.services;

import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceMateriel implements CRUD<Materiel> {
    public static Connection cnx;

    public ServiceMateriel() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Materiel materiel) throws SQLException {
        try {
            String req = "INSERT INTO pack_materiel(libelle, description, prix, image) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, materiel.getLibelle());
            ps.setString(2, materiel.getDescription());
            ps.setInt(3, materiel.getPrix());
            ps.setString(4, materiel.getImage());

            System.out.println("Materiel Added !");
            ps.executeUpdate();
            ps.close(); // Close PreparedStatement after use
        } catch (SQLException e) {
            throw new SQLException("Failed to insert materiel: " + e.getMessage());
        }
    }

    @Override
    public void updateOne(Materiel materiel) throws SQLException {
        try {
            String req = "UPDATE pack_materiel SET libelle=?, description=?, prix=?, image=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, materiel.getLibelle());
            ps.setString(2, materiel.getDescription());
            ps.setInt(3, materiel.getPrix());
            ps.setString(4, materiel.getImage());
            ps.setInt(5, materiel.getId()); // Correct indexing

            System.out.println("Materiel Updated !");
            ps.executeUpdate();
            ps.close(); // Close PreparedStatement after use
        } catch (SQLException e) {
            throw new SQLException("Failed to update materiel: " + e.getMessage());
        }
    }

    @Override
    public void deleteOne(Materiel materiel) throws SQLException {
        try {
            String req = "DELETE FROM `pack_materiel` WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, materiel.getId());

            System.out.println("Materiel Deleted !");
            ps.executeUpdate();
            ps.close(); // Close PreparedStatement after use
        } catch (SQLException e) {
            throw new SQLException("Failed to delete materiel: " + e.getMessage());
        }
    }

    @Override
    public List<Materiel> selectAll() throws SQLException {
        try {
            String req = "SELECT * FROM `pack_materiel`";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            List<Materiel> materiels = new ArrayList<>();
            while (rs.next()) {
                Materiel materiel = new Materiel();
                materiel.setId(rs.getInt("id"));
                materiel.setLibelle(rs.getString("libelle"));
                materiel.setDescription(rs.getString("description"));
                materiel.setPrix(rs.getInt("prix"));
                materiel.setImage(rs.getString("image"));
                materiels.add(materiel);
            }

            rs.close(); // Close ResultSet after use
            ps.close(); // Close PreparedStatement after use

            return materiels;
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch materiels: " + e.getMessage());
        }
    }
}
