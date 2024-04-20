package com.webandit.webuild.models;

import java.math.BigDecimal;
import java.util.List;

public class Offre {
    private int id;
    private String title;
    private String description;
    private float salary;
    private BigDecimal latitude;
    private  BigDecimal longitude;
    private List<Candidature> candidatures;

    public Offre() {}

    public Offre(String title, String description, float salary, BigDecimal latitude, BigDecimal longitude) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Offre(int id, String title, String description, float salary, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int
    getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
