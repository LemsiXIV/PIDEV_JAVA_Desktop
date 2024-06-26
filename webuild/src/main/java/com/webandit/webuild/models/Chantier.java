package com.webandit.webuild.models;

import javafx.scene.control.Button;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Chantier {
    //variabels
    private int id ,id_user;
    private String nom,description;
    private Date date;
    private float remuneration;
    private List<Tasks> tasks;

    //constructure
    public Chantier() {}
    public Chantier(int id, String nom) {
        this.id = id;
        this.nom = nom;


    }
    public Chantier(int id, String nom,String des,float rem) {
        this.id = id;
        this.nom = nom;
        this.description=des;
        this.remuneration=rem;

    }
    public Chantier( String nom, String description, Date date, float remuneration,int id_user) {

        this.nom = nom;
        this.description = description;
        this.date = date;
        this.remuneration = remuneration;
        this.id_user = id_user;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Tasks task)
    {
        tasks.add(task);
        task.setId_chantier(this);
    }
    //getters & setters

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
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
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    @Override
    public String toString() {
        return "Chantier{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", remuneration=" + remuneration +
                ", userid=" + id_user +
                '}';
    }
}
