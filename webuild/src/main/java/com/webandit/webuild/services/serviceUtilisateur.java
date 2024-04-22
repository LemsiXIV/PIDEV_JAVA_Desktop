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
        String req= "INSERT INTO `utilisateur`(`nom`, `prénom`, `téléphone`, `adresse`, `email`, `pwd`) VALUES"+
                " ('"+utilisateur.getNom()+"','"+utilisateur.getPrenom()+"','"+utilisateur.getTelephone()+"','"+utilisateur.getAdresse()+"','"+utilisateur.getEmail()+"','"+utilisateur.getPwd()+"')";
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
            p.setId(rs.getInt(1));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            personList.add(p);
        }
        return  personList;
    }
    public String Login (String email) throws SQLException {
        String req = "SELECT `pwd` FROM `utilisateur` WHERE `email` = '" + email + "'";
        Statement st = cnx.createStatement();
        ResultSet resultset =st.executeQuery(req);
        String pwd= null;
        if(resultset.next()){
            pwd= resultset.getString("pwd");
        }
        resultset.close();
        st.close();
        return pwd;
    }
     public ResultSet selectByEmail( String email) throws SQLException{
        String req="SELECT * FROM `utilisateur` WHERE `email` ='" + email + "'";
        Statement st= cnx.createStatement();
        ResultSet result=st.executeQuery(req);
        return result;

     }
}
