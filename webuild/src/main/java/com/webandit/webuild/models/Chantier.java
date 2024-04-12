package com.webandit.webuild.models;

import javafx.scene.control.Button;

import java.sql.Date;

public class Chantier {
    private int id ;
    private String nom,description;
    private Date date;
    private float remuneration;

    public Button getManage() {
        return Manage;
    }

    public void setManage(Button manage) {
        Manage = manage;
    }

    private Button Manage;

    public Chantier() {}
    public Chantier( String nom, String description, Date date, float remuneration) {

        this.nom = nom;
        this.description = description;
        this.date = date;
        this.remuneration = remuneration;
        this.Manage = new Button("Manage");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(float remuneration) {
        this.remuneration = remuneration;
    }

    @Override
    public String toString() {
        return "Chantier{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", remuneration=" + remuneration +
                '}';
    }
}
