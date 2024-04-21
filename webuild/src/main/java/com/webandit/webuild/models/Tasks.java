package com.webandit.webuild.models;

import java.sql.Date;

public class Tasks {

    //variabels
    private int id,status;
    private String name;
    private String priority;
    private String description;
    private String nomchantier;
    private Date due;
    private Chantier id_chantier;

    // Constructure
    public Tasks(){}

    public Tasks(  int id ,String name, String priority,int status, String description, Date due ,  String chnom) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.due = due;
        this.nomchantier = chnom;
    }
    public Tasks(  String name, String priority,int status, String description, Date due , Chantier chantier) {

        this.status = status;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.due = due;
        this.id_chantier = chantier;
    }
    public Tasks(  String name, String priority,int status, String description, Date due , String chnom) {

        this.status = status;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.due = due;
        this.nomchantier = chnom;
    }

    // toString affichafe

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", description='" + description + '\'' +
                ", due=" + due +
                ", nom chantier=" + nomchantier +
                '}';
    }


    // Getters & Setters
    public String getNomchantier() {
        return nomchantier;
    }

    public void setNomchantier(String nomchantier) {
        this.nomchantier = nomchantier;
    }

    public Chantier getId_chantier() {
        return id_chantier;
    }

    public void setId_chantier(Chantier id_chantier) {
        this.id_chantier = id_chantier;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }



}
