package com.webandit.webuild.test;

import java.sql.SQLException;



import com.webandit.webuild.models.Assurance;

import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceAssurance;

import com.webandit.webuild.services.ServiceDemande;
import com.webandit.webuild.utils.DBConnection;

import java.sql.Date;
import java.sql.SQLException;



public class main {


    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        Assurance a = new Assurance(3,"vie","desc","none","none.png","none", "none",22,55);
        Demande d = new Demande(1,a,100,1,Date.valueOf("2019-01-01"),Date.valueOf("2019-01-01"),"none", 1);

        ServiceAssurance sp = new ServiceAssurance();
        ServiceDemande sd = new ServiceDemande();
        try {
            sp.insertOne(a);
            System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }



      try {
        sd.insertOne(d);
        System.out.println(sd.selectAll());
    } catch (SQLException e) {
        System.err.println("Erreur: "+e.getMessage());
    }


}}

