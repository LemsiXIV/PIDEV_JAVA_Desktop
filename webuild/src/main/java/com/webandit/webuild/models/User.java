package com.webandit.webuild.models;

import java.util.Collection;
import java.util.Date;
import com.google.gson.Gson;
public class User {
    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String cin;
    private String fonction;
    private String address;
    private Date date;
    private String bio;
    private String rolesJson;
    private int is_Banned;
    private int is_verified;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRolesJson() {
        return rolesJson;
    }


    public void setRolesJson(String rolesJson) {
        this.rolesJson = rolesJson;
    }


    public int isIs_Banned() {
        return is_Banned;
    }

    public void setIs_Banned(int is_Banned) {
        this.is_Banned = is_Banned;
    }

    public int isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }
    public User(){}
    public User(int id, String email, String password, String nom, String prenom, String telephone, String cin, String fonction, String address, Date date, String bio, Collection<String> roles, int is_Banned, int is_verified) {
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
        setRoles(roles);
        this.is_Banned = is_Banned;
        this.is_verified = is_verified;
    }

    public User(String email, String password, String nom, String prenom, String telephone, String cin, String fonction, String address, Date date, String bio, Collection<String> roles, int is_Banned, int is_verified) {
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
        setRoles(roles);
        this.is_Banned = is_Banned;
        this.is_verified = is_verified;
    }
    //for profil update and delete
    public User(String nom, String prenom, String telephone, String address, String email, String cin, String fonction, Date date, String bio) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.cin = cin;
        this.fonction = fonction;
        this.address = address;
        this.date = date;
        this.bio = bio;
    }
    //for admin updating user
    public User(String email, String nom, String prenom, String telephone, String cin, String fonction, String address, Date date, String bio, Collection<String> roles, int is_Banned, int is_verified) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.cin = cin;
        this.fonction = fonction;
        this.address = address;
        this.date = date;
        this.bio = bio;
        setRoles(roles);
        this.is_Banned = is_Banned;
        this.is_verified = is_verified;
    }
    public void setRoles(Collection<String> roles) {
        Gson gson = new Gson();
        this.rolesJson = gson.toJson(roles);
    }

    public Collection<String> getRoles() {
        Gson gson = new Gson();
        return gson.fromJson(rolesJson, Collection.class);
    }
}