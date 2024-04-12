package com.webandit.webuild.models;
import java.util.Date;
public class Post {
    private int id;
    private String titre;
    private String description;
    private String auteur;
    private Date date;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", auteur='" + auteur + '\'' +
                ", date=" + date +
                '}';
    }

    public Post(int id, String titre, String description, String auteur, Date date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
    }

    public Post(String titre, String description, String auteur, Date date) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
    }

    public Post() {

    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getAuteur() {
        return auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
