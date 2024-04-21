package com.webandit.webuild.services;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemande implements CRUD<Demande> {

    private Connection cnx;

    public ServiceDemande() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Demande demande) throws SQLException {
        String query = "INSERT INTO demande (assurance_id, montant, user, date_debut, date_fin, commentaire, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getA().getId());
        pstmt.setInt(2, demande.getMontant());
        pstmt.setInt(3, demande.getUser());
        pstmt.setDate(4, demande.getDate_debut());
        pstmt.setDate(5, demande.getDate_fin());
        pstmt.setString(6, demande.getCommentaire());
        pstmt.setInt(7, demande.getStatus());
        pstmt.executeUpdate();
    }

    @Override
    public void updateOne(Demande demande) throws SQLException {
        String query = "UPDATE demande SET assurance_id = ?, montant = ?, user = ?, date_debut = ?, date_fin = ?, commentaire = ?, status = ? WHERE id_d = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getA().getId());
        pstmt.setInt(2, demande.getMontant());
        pstmt.setInt(3, demande.getUser());
        pstmt.setDate(4, demande.getDate_debut());
        pstmt.setDate(5, demande.getDate_fin());
        pstmt.setString(6, demande.getCommentaire());
        pstmt.setInt(7, demande.getStatus());
        pstmt.setInt(8, demande.getId_d());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteOne(Demande demande) throws SQLException {
        String query = "DELETE FROM demande WHERE id_d = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getId_d());
        pstmt.executeUpdate();
    }

    @Override
    public List<Demande> selectAll() throws SQLException {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM demande";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Demande demande = new Demande();
            demande.setId_d(rs.getInt("id_d"));
            ServiceAssurance serviceAssurance =new ServiceAssurance();
            Assurance assurance = serviceAssurance.selectOne(rs.getInt("assurance_id"));
            demande.setA(assurance); // Définir l'objet Assurance pour la demande
            demande.setMontant(rs.getInt("montant"));
            demande.setUser(rs.getInt("user"));
            demande.setDate_debut(rs.getDate("date_debut"));
            demande.setDate_fin(rs.getDate("date_fin"));
            demande.setCommentaire(rs.getString("commentaire"));
            demande.setStatus(rs.getInt("status"));
            demandes.add(demande);
        }
        return demandes;
    }
}
