package com.webandit.webuild.models;

public class Chantier {
    private int id ;
    private String nom,description;
    private String date;
    private float remuneration;

    public Chantier() {}
    public Chantier(int id, String nom, String description, String date, float remuneration) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.remuneration = remuneration;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
