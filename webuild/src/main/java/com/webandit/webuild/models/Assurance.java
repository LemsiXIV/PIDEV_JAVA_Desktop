package com.webandit.webuild.models;


public class Assurance {
    private int id;
    private String nom, description,image, condition_age,condition_medicale,condition_financiere;
    private int franchise , prime;


    public Assurance() {}

    public Assurance(int id, String nom, String description, String image, String condition_age, String condition_medicale,  String condition_financiere, int franchise, int prime) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.condition_age = condition_age;
        this.condition_medicale = condition_medicale;
        this.condition_financiere = condition_financiere;

        this.franchise = franchise;
        this.prime = prime;

    }

    public Assurance(int id, String vie, String desc, String none, String image, String none1, String none2, int i, int franchise, int prime) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCondition_age() {
        return condition_age;
    }

    public void setCondition_age(String condition_age) {
        this.condition_age = condition_age;
    }

    public String getCondition_medicale() {
        return condition_medicale;
    }

    public void setCondition_medicale(String condition_medicale) {
        this.condition_medicale = condition_medicale;
    }

    public String getCondition_financiere() {
        return condition_financiere;
    }

    public void setCondition_financiere(String condition_financiere) {
        this.condition_financiere = condition_financiere;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public int getPrime() {
        return prime;
    }

    public void setPrime(int prime) {
        this.prime = prime;
    }
    @Override
    public String toString() {
        return "Assurance{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", condition_age='" + condition_age + '\'' +
                ", condition_medicale='" + condition_medicale + '\'' +
                ", condition_financiere='" + condition_financiere + '\'' +
                ", franchise=" + franchise +
                ", prime=" + prime +
                '}';
    }

}
