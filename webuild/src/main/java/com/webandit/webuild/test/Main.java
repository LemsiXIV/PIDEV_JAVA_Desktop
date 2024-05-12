package com.webandit.webuild.test;

import java.sql.SQLException;
import com.webandit.webuild.utils.DBConnection;
import com.webandit.webuild.models.User;
import com.webandit.webuild.services.serviceUtilisateur;
import org.json.JSONException;

public class Main {
        public static void main (String[] args){
            DBConnection db1= DBConnection.getInstance();

            serviceUtilisateur sp=new serviceUtilisateur();
            User p1= new User();
            try{
                System.out.println(sp.selectAll());
            }catch (SQLException e){
                System.err.println(("Erreur: "+e.getMessage()));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

}
