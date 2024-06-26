package com.webandit.webuild.services;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceChantier implements CRUDL<Chantier> {
    private Connection cnx;

    public ServiceChantier() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Chantier person) throws SQLException {
        String req = "INSERT INTO `chantier`(`nom`, `description`, `remuneration`, `start_date`, `user_id`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            // Set parameter values
            st.setString(1, person.getNom());
            st.setString(2, person.getDescription());
            st.setDouble(3, person.getRemuneration());
            st.setDate(4, person.getDate());
            st.setInt(5, person.getId_user());

            // Execute the update
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Chantier Added !");
            } else {
                System.out.println("Failed to add Chantier !");
            }
        }
    }

    public void insertOne12(Chantier chantier) throws SQLException {
        String req = "INSERT INTO `chantier`(`nom`, `description`, `remuneration`, `start_date`) VALUES (?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, chantier.getNom());
        ps.setString(2, chantier.getDescription());
        ps.setDate(4, chantier.getDate());
        ps.setFloat(3, chantier.getRemuneration());



        System.out.println("Chantier Added !");
        ps.executeUpdate(req);
    }


    @Override
    public void updateOne(Chantier chantier) throws SQLException {
        String sql = "UPDATE chantier SET nom=?, description=?, remuneration=?, start_date=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, chantier.getNom());
            preparedStatement.setString(2, chantier.getDescription());
            preparedStatement.setFloat(3, chantier.getRemuneration());
            preparedStatement.setDate(4, chantier.getDate());
            preparedStatement.setInt(5, chantier.getId());

            preparedStatement.executeUpdate();
        }
    }
    public void updatepro(Chantier chantier) throws SQLException {
        String sql = "UPDATE chantier SET nom=?, description=?, remuneration=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, chantier.getNom());
            preparedStatement.setString(2, chantier.getDescription());
            preparedStatement.setFloat(3, chantier.getRemuneration());
            preparedStatement.setInt(4, chantier.getId());

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void deleteOne(int id) throws SQLException {
        String sql = "DELETE FROM chantier WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Chantier> selectAll() throws SQLException {
        List<Chantier> chantierList = new ArrayList<>();
        String req = "SELECT * FROM `chantier` ";
        Statement st = cnx.createStatement();
        ResultSet result = st.executeQuery(req);
        while (result.next()) {
            Chantier ch = new Chantier();
            ch.setId(result.getInt("id"));
            ch.setNom(result.getString("nom"));
            ch.setDescription(result.getString("description"));
            ch.setRemuneration(result.getFloat("remuneration"));
            ch.setDate(result.getDate("start_date"));
            chantierList.add(ch);
        }
        return chantierList;
    }
    public List<Chantier> selectAllByUser(int userId) throws SQLException {
        List<Chantier> chantierList = new ArrayList<>();
        String req = "SELECT * FROM `chantier` WHERE user_id = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            // Set the user_id parameter
            st.setInt(1, userId);

            // Execute the query
            ResultSet result = st.executeQuery();

            // Process the results
            while (result.next()) {
                Chantier ch = new Chantier();
                ch.setId(result.getInt("id"));
                ch.setNom(result.getString("nom"));
                ch.setDescription(result.getString("description"));
                ch.setRemuneration(result.getFloat("remuneration"));
                ch.setDate(result.getDate("start_date"));
                // Add the Chantier object to the list
                chantierList.add(ch);
            }
        }

        return chantierList;
    }

    @Override
    public List<Chantier> selectListDerou() throws SQLException {
        List<Chantier> chantierListDerou = new ArrayList<>();
        String req = "SELECT * FROM `chantier` ";
        Statement st = cnx.createStatement();
        ResultSet result = st.executeQuery(req);
        while (result.next()) {
            Chantier ch = new Chantier();
            ch.setId(result.getInt("id"));
            ch.setNom(result.getString("nom"));
            ch.setDescription(result.getString("description"));
            ch.setRemuneration(result.getFloat("remuneration"));
            ch.setDate(result.getDate("start_date"));
            chantierListDerou.add(ch);
        }
        return chantierListDerou;

    }
}
