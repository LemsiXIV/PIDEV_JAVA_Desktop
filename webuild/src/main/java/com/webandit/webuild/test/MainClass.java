package com.webandit.webuild.test;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import com.webandit.webuild.utils.DBConnection;

import java.sql.Date;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        // Convert the string date to a Date object
        Date date = Date.valueOf("2024-05-01");

        Chantier p = new Chantier("Ben Daoued", "Yosra", date, 15);

        ServiceChantier sp = new ServiceChantier();

        try {
            sp.insertOne(p);
           // System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
