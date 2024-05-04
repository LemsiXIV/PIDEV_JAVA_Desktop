package com.webandit.webuild.models;

import java.util.Date;

public class Utilisateur {
    private int id, telephone;
    private Date datenaiss;
    private String nom, prenom, fonction, adresse, email, pwd, role;
    private Boolean isActivated;

    public Utilisateur(int id, int telephone, Date datenaiss, String nom, String prenom, String fonction, String adresse, String email, String pwd, Boolean isActivated, String role) {
        this.id = id;
        this.telephone = telephone;
        this.datenaiss = datenaiss;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.adresse = adresse;
        this.email = email;
        this.pwd = pwd;
        this.isActivated = isActivated;
        this.role="User";
    }

    public Utilisateur( String nom, String prenom,int telephone, String adresse, String email, String pwd) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.pwd = pwd;
    }
    public Utilisateur( String nom, String prenom,int telephone, String adresse, String email) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;

    }
    public Utilisateur( int id,String nom, String prenom,int telephone, String adresse, String email, String pwd) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.id= id;
        this.pwd=pwd;

    }
    public Utilisateur(int telephone, Date datenaiss, String nom, String prenom, String fonction, String adresse, String email, String pwd, Boolean isActivated) {
        this.telephone = telephone;
        this.datenaiss = datenaiss;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.adresse = adresse;
        this.email = email;
        this.pwd = pwd;
        this.isActivated = isActivated;
    }

    public Utilisateur() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Date getDatenaiss() {
        return datenaiss;
    }

    public void setDatenaiss(Date datenaiss) {
        this.datenaiss = datenaiss;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Boolean getActive() {
        return isActivated;
    }

    public void setActive(Boolean active) {
        isActivated = true;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
