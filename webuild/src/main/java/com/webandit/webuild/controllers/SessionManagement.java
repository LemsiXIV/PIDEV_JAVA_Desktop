package com.webandit.webuild.controllers;
public class SessionManagement {

    private static SessionManagement instance;
    private String loggedInUserEmail;

    private int id;
    private String nom;
    private String email;
    private String pwd;
    private String prenom;
    private String adresse;
    private int telephone;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getTelephone() {
        return telephone;
    }

    public SessionManagement(int id, String nom, String email, String pwd, String prenom, String adresse, int telephone) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.pwd = pwd;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public static SessionManagement getInstance(int id,String nom, String email, String pwd, String prenom, String adresse, int telephone) {
        if (instance == null) {
            instance = new SessionManagement(id, nom, email,pwd,prenom,adresse,telephone);
        }
        return instance;
    }
    public static SessionManagement getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserSession has not been initialized.");
        }
        return instance;
    }

    public void cleanUserSession() {
        id = 0;
        nom = null;
        email = null;
        pwd = null;
        prenom = null;
        adresse = null;
        telephone = 0;
    }
    @Override
    public String toString() {
        return "UserSession{" +
                //"id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + pwd + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

}