package com.webandit.webuild.models;

import java.sql.Date;

public class Commentaire {

    // Variables
    private int id, nbrlikes, nbrdislikes,rate; // Changer post en postId
    private String contenu, nom;
    private Date dateCreation;
    private int post_id;
    // Constructeurs

    public Commentaire(String contenu, String nom, Date dateCreation, int nbrlikes, int nbrdislikes, int rate ,int post) { // Changer post en postId
        this.contenu = contenu;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.nbrlikes = nbrlikes;
        this.nbrdislikes = nbrdislikes;
        this.rate=rate;
        this.post_id = post; // Changer post en postId
    }

    public Commentaire(int nbrlikes, int nbrdislikes, int rate, String contenu, String nom, Date dateCreation, int post_id) {
        this.nbrlikes = nbrlikes;
        this.nbrdislikes = nbrdislikes;
        this.rate = rate;
        this.contenu = contenu;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.post_id = post_id;
    }

    public Commentaire(int i, String contenu2, String nom2, Date sqlDate, int i1, int i2, int i3) {
    }



    public int getNbrlikes() {
        return nbrlikes;
    }

    public void setNbrlikes(int nbrlikes) {
        this.nbrlikes = nbrlikes;
    }

    public int getNbrdislikes() {
        return nbrdislikes;
    }

    public void setNbrdislikes(int nbrdislikes) {
        this.nbrdislikes = nbrdislikes;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Commentaire() {

    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrLikes() {
        return nbrlikes;
    }

    public void setNbrLikes(int nbrlikes) {
        this.nbrlikes = nbrlikes;
    }

    public int getNbrDislikes() {
        return nbrdislikes;
    }

    public void setNbrDislikes(int nbrdislikes) {
        this.nbrdislikes = nbrdislikes;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getPost_id() { // Ajout de la méthode pour obtenir l'ID du post associé
        return post_id;
    }

    public void setPost_id(int post_id) { // Ajout de la méthode pour définir l'ID du post associé
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", nom='" + nom + '\'' +
                ", dateCreation=" + dateCreation +
                ", nbrlikes=" + nbrlikes +
                ", nbrdislikes=" + nbrdislikes +
                ", rate=" + rate +
                ", post_id=" + post_id + // Changer post en postId
                '}';
    }


}
