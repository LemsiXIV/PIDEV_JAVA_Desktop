package com.webandit.webuild.services;

import com.webandit.webuild.utils.DBConnection;
import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.models.Offre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ServiceCandidature implements CRUD<Candidature>{
    private Connection cnx;

    public ServiceCandidature() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Candidature candidature) throws SQLException {
        String req = "INSERT INTO `candidature`(`offre_id`, `id_client`, `description`, `email`,`cv`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, candidature.getOffre().getId());
        ps.setInt(2, candidature.getId_client());
        ps.setString(3, candidature.getDescription());
        ps.setString(4, candidature.getEmail());
        ps.setString(5, candidature.getCv());
        // Execute the prepared statement
        ps.executeUpdate();
        System.out.println("Candidature Added !");
    }

    @Override
    public void updateOne(Candidature candidature) throws SQLException {
        String req = "UPDATE `candidature` SET `offre_id`=?, `id_client`=?, `description`=?, `email`=? ,`cv`=? WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, candidature.getOffre().getId());
        ps.setInt(2, candidature.getId_client());
        ps.setString(3, candidature.getDescription());
        ps.setString(4, candidature.getEmail());
        ps.setInt(5, candidature.getId());
        ps.setString(6, candidature.getCv());

        ps.executeUpdate();
        System.out.println("Candidature Updated !");
    }

    @Override
    public void deleteOne(Candidature candidature) throws SQLException {
        String req = "DELETE FROM `candidature` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, candidature.getId());
        ps.executeUpdate();
        System.out.println("Candidature Deleted !");
    }
    public void deleteCan(int id) throws SQLException {
        String req = "DELETE FROM `candidature` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Candidature Deleted !");
    }

    @Override
    public List<Candidature> selectAll() throws SQLException {
        List<Candidature> candidatureList = new ArrayList<>();
        ServiceOffre serviceOffre = new ServiceOffre();
        String req = "SELECT * FROM `candidature`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Candidature c = new Candidature();

            c.setId(rs.getInt("id"));
            // Fetch Offre based on offre_id
            int offreId = rs.getInt("offre_id");
            Offre offre = serviceOffre.getOffreById(offreId); // Replace serviceOffre with the appropriate instance of ServiceOffre
            c.setOffre(offre);
            if (offre != null) {
                c.setOffreTitle(offre.getTitle());
            } else {
                c.setOffreTitle(""); // Set a default value if offre is null
            }
            c.setId_client(rs.getInt("id_client"));
            c.setDescription(rs.getString("description"));
            c.setEmail(rs.getString("email"));
            c.setCv(rs.getString("cv"));

            candidatureList.add(c);
        }

        return candidatureList;
    }

    public void deleteCandidatesByOfferId(int offerId) throws SQLException {
        String deleteQuery = "DELETE FROM candidature WHERE offre_id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(deleteQuery)) {
            statement.setInt(1, offerId);
            statement.executeUpdate();
        }
    }
    public List<Candidature> getCandidaturesByOffreTitle(String offreTitle) throws SQLException {
        List<Candidature> candidatures = new ArrayList<>();
        String query = "SELECT * FROM candidature WHERE offreTitle = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, offreTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Candidature candidature = new Candidature();
                    candidature.setId(resultSet.getInt("id"));
                    candidature.setOffreTitle(resultSet.getString("offreTitle"));
                    candidature.setDescription(resultSet.getString("description"));
                    candidature.setEmail(resultSet.getString("email"));
                    candidature.setCv(resultSet.getString("cv"));
                    candidatures.add(candidature);
                }
            }
        }
        return candidatures;
    }
    public List<Candidature> getCandidaturesByOffre(Offre offre) throws SQLException {
        String req = "SELECT * FROM `candidature` WHERE `offre_id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, offre.getId()); // Assuming Offre has a method getId() to retrieve its ID

        ResultSet rs = ps.executeQuery();
        List<Candidature> candidatures = new ArrayList<>();

        while (rs.next()) {
            // Construct Candidature objects from the result set
            Candidature candidature = new Candidature();
            // Set properties of the candidature object from the result set
            candidature.setId(rs.getInt("id"));
            // Set other properties of the candidature object...
            candidatures.add(candidature);
        }

        return candidatures;
    }

    public void updatepro(Candidature candidature) throws SQLException {
        String req = "UPDATE `candidature` SET  `description`=?, `email`=? ,`cv`=? WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, candidature.getDescription());
        ps.setString(2, candidature.getEmail());
        ps.setString(3, candidature.getCv());
        ps.setInt(4, candidature.getId());

        ps.executeUpdate();
        System.out.println("Candidature Updated !");
    }
}

