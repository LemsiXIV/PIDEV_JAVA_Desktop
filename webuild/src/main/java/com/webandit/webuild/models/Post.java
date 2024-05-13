package com.webandit.webuild.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private int id;

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    private int idclient;
    private String titre;
    private String description;
    private String auteur;
    private Date date;
    private String img;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private List<Commentaire> commentaires;




    //getters & setters

    public List<Commentaire> getCommentaire() {
        return commentaires;
    }


    public Post( String titre, String description, String auteur, Date date, String img) {

        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
        this.img = img;
        this.commentaires = new ArrayList<>();
    }

    public Post( String titre, String description, String auteur, Date date, String img , int idclient) {

        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
        this.img = img;
        this.idclient=idclient;
        this.commentaires = new ArrayList<>();
    }

    public Post(int id, String titre, String description, String auteur, Date date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
        this.commentaires = new ArrayList<>();
    }

    public Post(String titre, String description, String auteur, Date date) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.date = date;
        this.commentaires = new ArrayList<>();
    }

    public Post() {
        this.commentaires = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

  /*  public List<Commentaire> getCommentaires() {
        return commentaires;
    }*/

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public void addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", auteur='" + auteur + '\'' +
                ", date=" + date +
                ", commentaires=" + commentaires +
                '}';
    }
}
