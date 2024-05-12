package com.webandit.webuild.controllers;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;

public class SessionManagement {

    private static SessionManagement instance;
    private String loggedInUserEmail;

    private int id;
    private String nom;
    private String email;
    private String password;
    private String prenom;
    private String address;
    private String telephone;
    private Collection<String> roles;
    private int is_verified;
    private int is_Banned;
    private Date date;
    private String fonction;
    private String cin;
    private String bio;





    public Collection<String> getRoles() {
        return roles;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getAddress() {
        return address;
    }
    public String  getTelephone() {
        return telephone;
    }
    public String  getCin() {
        return cin;
    }
    public String  getFonction() {
        return fonction;
    }
    public String  getBio() {
        return bio;
    }
    public Date  getDate() {
        return date;
    }
    public int isIs_verified() { return is_verified; }
    public int isIs_Banned() { return is_Banned; }


    public SessionManagement(int id, String email, String password, String nom, String prenom, String telephone, String cin, String fonction, String address, Date date, String bio, Collection<String> roles, int is_Banned, int is_verified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.cin = cin;
        this.fonction = fonction;
        this.address = address;
        this.date = date;
        this.bio = bio;
        this.roles = roles;
        this.is_Banned = is_Banned;
        this.is_verified = is_verified;
    }

    public static void setInstance(SessionManagement instance) {
        SessionManagement.instance = instance;
    }

    public static SessionManagement getInstance(int id, String email, String password, String nom, String prenom, String telephone, String cin, String fonction, String address, Date date, String bio, Collection<String> roles, int is_Banned, int is_verified) {
        if (instance == null) {
            instance = new SessionManagement(  id,  email,  password,  nom,  prenom,  telephone,  cin,  fonction,  address,  date,  bio,  roles,  is_Banned,  is_verified);
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
        id=0;
        email=null;
        password=null;
        nom=null;
        prenom=null;
        telephone=null;
        cin=null;
        fonction=null;
        address=null;
        date=null;
        bio=null;
        roles=null;
        is_verified=0;
        is_Banned=0;
    }
    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", roles='" + roles + '\'' +
                ", is_verified='" + is_verified + '\'' +
                ", is_Banned='" + is_Banned + '\'' +
                ", fonction='" + fonction + '\'' +
                ", cin='" + cin + '\'' +
                ", date='" + date + '\'' +
                ", bio='" + bio + '\'' +

                '}';
    }

}