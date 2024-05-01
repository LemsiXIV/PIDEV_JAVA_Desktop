    package com.webandit.webuild.models;

    import java.util.Objects;

    public class Materiel {
        private int id;
        private String libelle;
        private String description;
        private int prix;
        private String image;

        // Constructeur

        public Materiel(String libelle, String description, int prix, String image) {
            this.libelle = libelle;
            this.description = description;
            this.prix = prix;
            this.image = image;
        }

        // Default constructor
        public Materiel() {
        }

        // Getters et setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPrix() {
            return prix;
        }

        public void setPrix(int prix) {
            this.prix = prix;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        // Méthode pour afficher les informations sur le matériel


        // Méthode toString
        @Override
        public String toString() {
            return "Materiel{" +
                    "id=" + id +
                    ", libelle='" + libelle + '\'' +
                    ", description='" + description + '\'' +
                    ", prix=" + prix +
                    ", image='" + image + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Materiel materiel)) return false;
            return getId() == materiel.getId() && getPrix() == materiel.getPrix() && Objects.equals(getLibelle(), materiel.getLibelle()) && Objects.equals(getDescription(), materiel.getDescription()) && Objects.equals(getImage(), materiel.getImage());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getLibelle(), getDescription(), getPrix(), getImage());
        }


    }
