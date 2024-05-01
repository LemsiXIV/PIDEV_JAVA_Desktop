package com.webandit.webuild.controllers;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.webandit.webuild.models.Offre;

public class card {
    @FXML
    private ImageView img;

    @FXML
    private Label titre;

    @FXML
    private Label descrp;
    @FXML
    private Label salary;

    private Offre offre;

    public void getData(Offre offre) {
        this.offre = offre;
        titre.setText(offre.getTitle());
        descrp.setText(offre.getDescription());
        salary.setText(String.valueOf(offre.getSalary()));


    }

}
