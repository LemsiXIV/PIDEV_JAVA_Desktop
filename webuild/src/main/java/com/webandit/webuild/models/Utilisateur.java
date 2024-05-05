package com.webandit.webuild.models;

import java.util.Date;
import org.json.JSONObject;
public class Utilisateur {
    private int id, telephone,cin;
    private Date datenaiss;
    private String nom, prenom, fonction, adresse, email, password,bio;
    private int is_verified;
    private String roles;


    public Utilisateur(int id, int telephone, Date datenaiss, String nom, String prenom, String fonction, String adresse, String email, String pwd, String roles,int is_verified) {
        this.id = id;
        this.telephone = telephone;
        this.datenaiss = datenaiss;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.adresse = adresse;
        this.email = email;
        this.password = pwd;

        this.roles = roles;

            this.is_verified = 0;

    }

    public Utilisateur( String nom, String prenom,int telephone, String adresse, String email, String pwd, Integer is_verified) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.password = pwd;
        if (is_verified != null) {
            this.is_verified = is_verified;
        } else {
            this.is_verified = 0;
        }
    }
    public Utilisateur( String nom, String prenom,int telephone, String adresse, String email) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;


    }
    public Utilisateur( int id,String nom, String prenom,int telephone, String adresse, String email, String pwd, String roles) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.id= id;
        this.password=pwd;
        this.roles = roles;

    }
    public Utilisateur( int id,String nom, String prenom,int telephone, String adresse, String email, String pwd, String roles,int is_verified) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.id= id;
        this.password=pwd;
        this.roles = roles;
        this.is_verified=is_verified;

    }
    public Utilisateur(int telephone, Date datenaiss, String nom, String prenom, String fonction, String adresse, String email, String pwd, int is_verified) {
        this.telephone = telephone;
        this.datenaiss = datenaiss;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.adresse = adresse;
        this.email = email;
        this.password = pwd;
        this.is_verified=is_verified;
    }

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, int telephone, String adresse, String email, String pwd, String role) {
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.password=pwd;
        this.roles = role;

    }

    public Utilisateur(String nom, String prenom, int telephone, String adresse, String email, String pwd, String defaultRole, int is_verified) {
        this.nom=nom;
        this.prenom=prenom;
        this.telephone=telephone;
        this.adresse=adresse;
        this.email=email;
        this.password=pwd;
        this.roles=defaultRole;
        this.is_verified=is_verified;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Integer is_verified) {
        this.is_verified=is_verified;
    }


    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
