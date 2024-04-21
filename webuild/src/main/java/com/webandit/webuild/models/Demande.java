package com.webandit.webuild.models;

import java.sql.Date;

public class Demande {
   int id_d ,montant ,user ;
   Date date_debut,date_fin ;
   String commentaire ;
   int status;
   Assurance a;

   public Demande() {

   }

   public Demande(int id_d, Assurance a, int montant, int user, Date date_debut, Date date_fin, String commentaire, int status) {
      this.id_d = id_d;
      this.montant = montant;
      this.date_debut = date_debut;
      this.date_fin = date_fin;
      this.commentaire = commentaire;
      this.status = status;
      this.a = a;
      this.user = user;
   }


   public int getId_d() {
      return id_d;
   }

   public void setId_d(int id_d) {
      this.id_d = id_d;
   }

   public int getMontant() {
      return montant;
   }

   public void setMontant(int montant) {
      this.montant = montant;
   }

   public Date getDate_debut() {
      return date_debut;
   }

   public void setDate_debut(Date date_debut) {
      this.date_debut = date_debut;
   }

   public Date getDate_fin() {
      return date_fin;
   }

   public void setDate_fin(Date date_fin) {
      this.date_fin = date_fin;
   }

   public String getCommentaire() {
      return commentaire;
   }

   public void setCommentaire(String commentaire) {
      this.commentaire = commentaire;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public Assurance getA() {
      return a;
   }

   public int getUser() {
      return user;
   }

   public void setUser(int user) {
      this.user = user;
   }

   @Override
   public String toString() {
      return "Demande{" +
              "id_d=" + id_d +
              ", montant=" + montant +
              ", user=" + user +
              ", date_debut=" + date_debut +
              ", date_fin=" + date_fin +
              ", commentaire='" + commentaire + '\'' +
              ", status=" + status +
              ", a=" + a +
              '}';
   }

   public void setA(Assurance a) {
      this.a = a;
   }
}
