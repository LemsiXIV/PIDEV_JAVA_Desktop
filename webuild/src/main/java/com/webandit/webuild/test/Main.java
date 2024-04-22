package com.webandit.webuild.test;

import java.sql.SQLException;
import com.webandit.webuild.utils.DBConnection;
import com.webandit.webuild.models.Utilisateur;
import com.webandit.webuild.services.serviceUtilisateur;
public class Main {
        public static void main (String[] args){
            DBConnection db1= DBConnection.getInstance();

            serviceUtilisateur sp=new serviceUtilisateur();
            Utilisateur p1= new Utilisateur();
            try{
                System.out.println(sp.selectAll());
            }catch (SQLException e){
                System.err.println(("Erreur: "+e.getMessage()));
            }
        }

}
