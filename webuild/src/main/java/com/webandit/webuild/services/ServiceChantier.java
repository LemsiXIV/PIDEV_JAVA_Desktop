package com.webandit.webuild.services;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceChantier implements CRUD<Chantier> {
    private Connection cnx;

    public ServiceChantier() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Chantier person) throws SQLException {
        String req = "INSERT INTO `chantier`(`nom`, `description`, `remuneration`, `start_date`) VALUES " +
                "('"+person.getNom()+"','"+person.getDescription()+"',"+person.getRemuneration()+",'"+person.getDate()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Person Added !");
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


}
