package com.webandit.webuild.services;

import com.webandit.webuild.models.User;
import com.webandit.webuild.utils.DBConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class serviceUtilisateur implements CRUD<User>{
    private Connection cnx;
    public serviceUtilisateur() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(User utilisateur) throws SQLException {
        String req= "INSERT INTO `user`(`email`, `roles`, `password`, `nom`, `prenom`, `telephone`, `cin`, `fonction`, `is_banned`, `address`, `date`, `is_verified`, `bio`) VALUES"+
                " ('"+utilisateur.getEmail()+"','"+utilisateur.getRoles()+"','"+utilisateur.getPassword()+"','"+utilisateur.getNom()+"','"+utilisateur.getPrenom()+"','"+utilisateur.getTelephone()+"','"+utilisateur.getCin()+"','"+utilisateur.getFonction()+"','"+utilisateur.isIs_Banned()+"','"+utilisateur.getAddress()+"','"+utilisateur.getDate()+"','"+utilisateur.isIs_verified()+"','"+utilisateur.getBio()+"')";
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        st.close();
    }

    @Override
    public void updateOne(User utilisateur) throws SQLException {
        String req= "UPDATE user SET nom = '" + utilisateur.getNom() + "', " +
                "prenom = '" + utilisateur.getPrenom() + "', " +
                "telephone = '" + utilisateur.getTelephone() + "', " +
                "address = '" + utilisateur.getAddress() + "', " +
                "email = '" + utilisateur.getEmail() + "', " +
                "cin = '" + utilisateur.getCin() + "', " +
                "fonction = '" + utilisateur.getFonction() + "', " +
                "date = '" + new java.sql.Date(utilisateur.getDate().getTime()) + "', " +
                "bio = '" + utilisateur.getBio() + "' " +
                "WHERE email = '" + utilisateur.getEmail() + "'";
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        st.close();
    }

    @Override
    public void deleteOne(User utilisateur) throws SQLException {
        String req = "DELETE FROM `user` WHERE `email` = '" + utilisateur.getEmail() + "'";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        st.close();
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> personList= new ArrayList<>();

        String req="SELECT * FROM `user`";
        Statement st =cnx.createStatement();
        //executeQuery car on va rien changer dans la base
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            User p = new User();
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setTelephone(rs.getString("telephone"));
            p.setAddress(rs.getString("address"));
            p.setEmail(rs.getString("email"));
            Collection<String> roles = new ArrayList<>();
            // Assuming 'roles' is stored as a comma-separated string in the database
            String rolesStr = rs.getString("roles");
            if (rolesStr != null && !rolesStr.isEmpty()) {
                String[] rolesArray = rolesStr.split(",");
                for (String role : rolesArray) {
                    roles.add(role.trim()); // Trim to remove leading/trailing whitespace
                }
            }
            p.setRoles(roles);


            personList.add(p);
        }
        return  personList;
    }
    public User Login (String mail, String pwd) throws SQLException {
        String req = "SELECT * FROM `user` WHERE `email` = '" + mail + "' AND `password` = '" + pwd + "'";
        Statement st = cnx.createStatement();
        ResultSet resultset =st.executeQuery(req);
        try (resultset){
            if(resultset.next()){
                int id= resultset.getInt("id");
                String nom = resultset.getString("nom");
                String email = resultset.getString("email");
                String password = resultset.getString("password");
                String prenom = resultset.getString("prenom");
                String fonction = resultset.getString("fonction");
                String address = resultset.getString("address");
                String telephone=resultset.getString("telephone");
                Collection<String> roles = new ArrayList<>();
                // Assuming 'roles' is stored as a comma-separated string in the database
                String rolesStr = resultset.getString("roles");
                int is_Banned=resultset.getInt("is_Banned");
                int is_verified=resultset.getInt("is_verified");
                Date date=resultset.getDate("date");
                String cin=resultset.getString("cin");
                String bio=resultset.getString("bio");
                if (rolesStr != null && !rolesStr.isEmpty()) {
                    String[] rolesArray = rolesStr.split(",");
                    for (String role : rolesArray) {
                        roles.add(role.trim()); // Trim to remove leading/trailing whitespace
                    }
                }
                return new User(id, email, password, nom, prenom, telephone, cin, fonction, address, date, bio, roles, is_Banned, is_verified);
            } else {
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public boolean selectByEmail( String email) throws SQLException{
        String req="SELECT * FROM `user` WHERE `email` ='" + email + "'";
        Statement st= cnx.createStatement();
        ResultSet result=st.executeQuery(req);
        boolean emailExists = result.next();
        result.close();
        st.close();
        return emailExists;

    }
    public ResultSet selectData( String email) throws SQLException{
        String req="SELECT * FROM `user` WHERE `email` ='" + email + "'";
        Statement st= cnx.createStatement();
        ResultSet resultset =st.executeQuery(req);
        return resultset;
    }
    public void updatePassword(String email, String password) throws SQLException {
        //String passwordencrypted = encrypt(password);

        String query = "UPDATE `user` SET `password` = '" + password + "' WHERE `email` = '" + email + "'";
        Statement statement = cnx.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }
    public void updateIsVerified(String email) throws SQLException {
        String sql = "UPDATE `user` SET is_verified = 1 WHERE email = '" + email + "'";
        Statement statement = cnx.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }
    public String getPasswordByEmail(String email) throws SQLException {
        String req = "SELECT `password` FROM `user` WHERE `email` = '" + email + "'";
        Statement st = cnx.createStatement();
        ResultSet result = st.executeQuery(req);

        String password = null;
        if (result.next()) {
            password = result.getString("password");
        }

        result.close();
        st.close();

        return password;
    }


}
