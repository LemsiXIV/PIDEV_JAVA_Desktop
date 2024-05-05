package com.webandit.webuild.services;

import com.webandit.webuild.models.Utilisateur;
import com.webandit.webuild.utils.DBConnection;

import java.net.MulticastSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class serviceUtilisateur implements CRUD<Utilisateur>{
    private Connection cnx;
    public serviceUtilisateur() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Utilisateur utilisateur) throws SQLException {
        String req= "INSERT INTO `utilisateur`(`nom`, `prénom`, `téléphone`, `adresse`, `email`, `pwd`,`roles`) VALUES"+
                " ('"+utilisateur.getNom()+"','"+utilisateur.getPrenom()+"','"+utilisateur.getTelephone()+"','"+utilisateur.getAdresse()+"','"+utilisateur.getEmail()+"','"+utilisateur.getPassword()+"','"+utilisateur.getRoles()+"')";
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        st.close();
    }

    @Override
    public void updateOne(Utilisateur utilisateur) throws SQLException {
        String req = "UPDATE utilisateur SET nom = '" + utilisateur.getNom() + "', prénom = '" + utilisateur.getPrenom() + "', téléphone = " + utilisateur.getTelephone() + ", adresse = '" + utilisateur.getAdresse() + "' WHERE email = '" + utilisateur.getEmail() + "'";
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        st.close();
    }

    @Override
        public void deleteOne(Utilisateur utilisateur) throws SQLException {
            String req = "DELETE FROM `utilisateur` WHERE `email` = '" + utilisateur.getEmail() + "'";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            st.close();
    }

    @Override
    public List<Utilisateur> selectAll() throws SQLException {
        List<Utilisateur> personList= new ArrayList<>();

        String req="SELECT * FROM `utilisateur`";
        Statement st =cnx.createStatement();
        //executeQuery car on va rien changer dans la base
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            Utilisateur p = new Utilisateur();
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prénom"));
            p.setTelephone(rs.getInt("téléphone"));
            p.setAdresse(rs.getString("adresse"));
            p.setEmail(rs.getString("email"));
            p.setRoles(rs.getString("roles"));


            personList.add(p);
        }
        return  personList;
    }
    public Utilisateur Login (String mail, String password) throws SQLException {
        String req = "SELECT * FROM `utilisateur` WHERE `email` = '" + mail + "' AND `pwd` = '" + password + "'";
        Statement st = cnx.createStatement();
        ResultSet resultset =st.executeQuery(req);
        try (resultset){
            if(resultset.next()){
               //int id= resultset.getInt("id");
                String nom = resultset.getString("nom");
                String email = resultset.getString("email");
                String pwd = resultset.getString("pwd");
                String prenom = resultset.getString("prénom");
                //String fonction = resultset.getString("fonction_utilisateur");
                String adresse = resultset.getString("adresse");
                int num=resultset.getInt("téléphone");
                String roles = resultset.getString("roles");
                return new Utilisateur(nom, prenom,num,  adresse,  email, pwd, roles);
            }else {
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
     public boolean selectByEmail( String email) throws SQLException{
        String req="SELECT * FROM `utilisateur` WHERE `email` ='" + email + "'";
        Statement st= cnx.createStatement();
        ResultSet result=st.executeQuery(req);
        boolean emailExists = result.next();
         result.close();
         st.close();
        return emailExists;

     }
    public ResultSet selectData( String email) throws SQLException{
        String req="SELECT * FROM `utilisateur` WHERE `email` ='" + email + "'";
        Statement st= cnx.createStatement();
        ResultSet resultset =st.executeQuery(req);
        return resultset;
    }
    public void updatePassword(String email, String password) throws SQLException {
        //String passwordencrypted = encrypt(password);

        String query = "UPDATE `utilisateur` SET `pwd` = '" + password + "' WHERE `email` = '" + email + "'";
        Statement statement = cnx.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }
    public void updateIsVerified(String email) throws SQLException {
        String sql = "UPDATE utilisateur SET is_verified = 1 WHERE email = '" + email + "'";
        Statement statement = cnx.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

}
