package com.webandit.webuild.services;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemande implements CRUD<Demande> {

    private Connection cnx;

    public ServiceDemande() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Demande demande) throws SQLException {
        String query = "INSERT INTO demande (assurance_id, montant,  date_debut, date_fin, commentaire, status,user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getA().getId());
        pstmt.setInt(2, demande.getMontant());

        pstmt.setDate(3, demande.getDate_debut());
        pstmt.setDate(4, demande.getDate_fin());
        pstmt.setString(5, demande.getCommentaire());
        pstmt.setInt(6, demande.getStatus());
        pstmt.setInt(7, demande.getUser());
        pstmt.executeUpdate();
    }

    @Override
    public void updateOne(Demande demande) throws SQLException {
        String query = "UPDATE demande SET assurance_id = ?, montant = ?, date_debut = ?, date_fin = ?, commentaire = ?, status = ?, user_id = ? WHERE id_d = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, demande.getA().getId());
        pstmt.setInt(2, demande.getMontant());

        pstmt.setDate(3, demande.getDate_debut());
        pstmt.setDate(4, demande.getDate_fin());
        pstmt.setString(5, demande.getCommentaire());
        pstmt.setInt(6, demande.getStatus());
        pstmt.setInt(7, demande.getUser());
        pstmt.setInt(8, demande.getId_d());
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected == 1) {
            System.out.println("Demande updated successfully.");
        } else {
            System.out.println("Failed to update demande.");
        }
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
            demande.setUser(rs.getInt("user_id"));
            demande.setDate_debut(rs.getDate("date_debut"));
            demande.setDate_fin(rs.getDate("date_fin"));
            demande.setCommentaire(rs.getString("commentaire"));
            demande.setStatus(rs.getInt("status"));
            demandes.add(demande);
        }
        return demandes;
    }
    public void updateStatus(int demandeId, int status) throws SQLException {
        String query = "UPDATE demande SET status = ? WHERE id_d = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, status);
        pstmt.setInt(2, demandeId);
        pstmt.executeUpdate();
    }
    // Method to validate a demande based on montant and duration
    public boolean validDemande(Demande demande) {
        try {
            // Check if montant is less than 3000 and duration is more than a year
            if (demande.getMontant() < 3000 && calculateDurationInDays(demande.getDate_debut(), demande.getDate_fin())>365) {
                // If conditions are met, update status to 1
                demande.setStatus(1);
                // Update the demande in the database
                updateOne(demande);
                // Return true to indicate success
                return true;
            } else {
                // Return false to indicate failure
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately
            return false;
        }
    }


    // Helper method to calculate duration between two dates in days
    private long calculateDurationInDays(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return diffInMillies / (24 * 60 * 60 * 1000);
    }
    public Assurance selectOne(int id) throws SQLException {
        String query = "SELECT * FROM assurance WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Assurance assurance = new Assurance();
            assurance.setId(rs.getInt("id"));
            assurance.setNom(rs.getString("nom"));
            assurance.setDescription(rs.getString("description"));
            assurance.setImage(rs.getString("image"));
            assurance.setCondition_age(rs.getString("condition_age"));
            assurance.setCondition_medicale(rs.getString("condition_medicale"));
            assurance.setCondition_financiere(rs.getString("condition_financiere"));
            assurance.setFranchise(rs.getInt("franchise"));
            assurance.setPrime(rs.getInt("prime"));
            return assurance;
        }
        return null; // Retourner null si aucune assurance correspondante n'est trouvée
    }

}
