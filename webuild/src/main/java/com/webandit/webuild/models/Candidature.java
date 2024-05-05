package com.webandit.webuild.models;

import java.math.BigDecimal;
import java.sql.Blob;
public class Candidature {

    private int id;
    private Offre offre;
    private String offreTitle;

    private int id_client;
    private String description;

    private  String email;
    private String cv;


    public Candidature() {};

    public Candidature(int id, Offre offre, String offreTitle, int id_client, String description, String email,String cv) {
        this.id = id;
        this.offre = offre;
        this.offreTitle = offreTitle;
        this.id_client = id_client;
        this.description = description;
        this.email = email;
        this.cv=cv;
    }
    public Candidature(int id, String description, String email,String cv) {
        this.id = id;
        this.description = description;
        this.email = email;
        this.cv=cv;
    }

    public int getId() {
        return id;
    }

    public Offre getOffre() {
        return offre;
    }

    public int getId_client() {
        return id_client;
    }

    public String getDescription() {
        return description;
    }


    public String getEmail() {
        return email;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getOffreTitle() {
        return offreTitle;
    }

    public void setOffreTitle(String offreTitle) {
        this.offreTitle = offreTitle;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "Candidature{" +
                "id=" + id +
                ", offre=" + offre +
                ", offreTitle='" + offreTitle + '\'' +
                ", id_client=" + id_client +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", cv='" + cv + '\'' +
                '}';
    }
}
