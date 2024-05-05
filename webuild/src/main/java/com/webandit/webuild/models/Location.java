package com.webandit.webuild.models;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class
Location {
    private int id_l;
    private int id_user;
    private Date date_d;
    private Date date_f;
    private Materiel m;

    public Location() {
    }

    public Location(int id_l, int id_user, Date date_d, Date date_f, Materiel m) {
        this.id_l = id_l;
        this.id_user = id_user;
        this.date_d = date_d;
        this.date_f = date_f;
        this.m = m;
    }

    public int getId_l() {
        return id_l;
    }

    public void setId_l(int id_l) {
        this.id_l = id_l;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate_d() {
        return date_d;
    }

    public void setDate_d(Date date_d) {
        this.date_d = date_d;
    }

    public Date getDate_f() {
        return date_f;
    }

    public void setDate_f(Date date_f) {
        this.date_f = date_f;
    }

    public Materiel getM() {
        return m;
    }

    public void setM(Materiel m) {
        this.m = m;
    }

    public String getDate_dString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date_d);
    }

    public String getDate_fString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date_f);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id_l=" + id_l +
                ", id_user=" + id_user +
                ", date_d=" + date_d +
                ", date_f=" + date_f +
                ", m=" + m +
                '}';
    }
}
