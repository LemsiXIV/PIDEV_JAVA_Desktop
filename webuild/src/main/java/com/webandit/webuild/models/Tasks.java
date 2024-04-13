package com.webandit.webuild.models;

import java.sql.Date;

public class Tasks {

    //variabels
    private int id,status;
    private String name,priority,description;
    private Date due;
    private Chantier id_chantier;

    // Constructure
    public Tasks(){}

    public Tasks(  String name, String priority,int status, String description, Date due , Chantier chantier) {

        this.status = status;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.due = due;
        this.id_chantier = chantier;
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
                ", id_chantier=" + id_chantier +
                '}';
    }


    // Getters & Setters


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
