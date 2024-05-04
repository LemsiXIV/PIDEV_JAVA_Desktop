package com.webandit.webuild.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.utils.DBConnection;



public  class ServiceAssurance implements CRUD<Assurance>{

    private Connection cnx ;

    public ServiceAssurance() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Assurance assurance) throws SQLException {
        String req = "INSERT INTO `assurance`(`nom`, `description`, `image`, `condition_age`, `condition_medicale`, `condition_financiere`,  `franchise`, `prime`) VALUES " +
                "('"+assurance.getNom()+"','"+assurance.getDescription()+"','"+assurance.getImage()+"','"+assurance.getCondition_age()+"','"+assurance.getCondition_medicale()+"','"+assurance.getCondition_financiere()+"','"+assurance.getFranchise()+"','"+assurance.getPrime()+"' )";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Assurance Added !");

    }

    public void insertOne1(Assurance assurance) throws SQLException {
        String req = "INSERT INTO `assurance`(`nom`, `description`, `image`, `condition_age`, `condition_medicale`, `condition_financiere`, `duree_couv`, `franchise`, `prime`) VALUES " +
                "(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, assurance.getNom());
        ps.setString(2, assurance.getDescription());
        ps.setString(3, assurance.getImage());
        ps.setString(4, assurance.getCondition_age());
        ps.setString(5, assurance.getCondition_medicale());
        ps.setString(6, assurance.getCondition_financiere());

        ps.setInt(8, assurance.getFranchise());
        ps.setInt(9, assurance.getPrime());


        ps.executeUpdate(req);
    }

    @Override
    public void updateOne(Assurance assurance) throws SQLException {
        String query = "UPDATE assurance SET nom = ?, description = ?, image = ?, "
                + "condition_age = ?, condition_medicale = ?, condition_financiere = ?, "
                + "franchise = ?, prime = ? WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setString(1, assurance.getNom());
            pstmt.setString(2, assurance.getDescription());
            pstmt.setString(3, assurance.getImage());
            pstmt.setString(4, assurance.getCondition_age());
            pstmt.setString(5, assurance.getCondition_medicale());
            pstmt.setString(6, assurance.getCondition_financiere());
            pstmt.setInt(7, assurance.getFranchise());
            pstmt.setInt(8, assurance.getPrime());
            pstmt.setInt(9, assurance.getId());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteOne(Assurance assurance) throws SQLException {
        String query = "DELETE FROM assurance WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, assurance.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Assurance> selectAll() throws SQLException {
        List<Assurance> assuranceList = new ArrayList<>();
        String req = "SELECT * FROM `assurance`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Assurance a = new Assurance();
            a.setId(rs.getInt((1)));
            a.setNom(rs.getString((2)));
            a.setDescription(rs.getString((3)));
            a.setImage(rs.getString((4)));
            a.setCondition_age(rs.getString((5)));
            a.setCondition_medicale(rs.getString((6)));
            a.setCondition_financiere(rs.getString((7)));
            a.setFranchise(rs.getInt((8)));
            a.setPrime(rs.getInt((9)));
            assuranceList.add(a);
        }

        return assuranceList;
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


    public List<Assurance> rechercherOffres(String searchTerm) throws SQLException {
        List<Assurance> searchResults = new ArrayList<>();
        String query = "SELECT * FROM assurance WHERE nom LIKE ? OR description LIKE ? ";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                   Assurance assurance= new Assurance();

                    assurance.setId(resultSet.getInt("id"));
                    assurance.setNom(resultSet.getString("nom"));
                    assurance.setDescription(resultSet.getString("description"));
                    assurance.setImage(resultSet.getString("image"));
                    assurance.setCondition_age(resultSet.getString("condition_age"));
                    assurance.setCondition_medicale(resultSet.getString("condition_medicale"));
                    assurance.setCondition_financiere(resultSet.getString("condition_financiere"));
                    assurance.setFranchise(resultSet.getInt("franchise"));
                    assurance.setPrime(resultSet.getInt("prime"));
                    searchResults.add(assurance);
                }
            }
        }
        return searchResults;
    }}

