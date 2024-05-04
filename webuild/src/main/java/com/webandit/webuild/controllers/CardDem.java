package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.webandit.webuild.models.Assurance;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CardDem {






    @FXML
    private Label Nom;

    @FXML
    private Label montant;

    @FXML
    private Label datedeb;

    @FXML
    private Label dateffin;

    @FXML
    private Label com;


    private Demande demande;


    public void setData(Demande demande) {
        this.demande = demande;
        // Set image

        Nom.setText("Type d'assurance demandée"+demande.getA().getNom());
        montant.setText("Le montant de couverture souhaité: "+demande.getMontant());

        datedeb.setText("date de début de couverture: " + demande.getDate_debut());
       dateffin.setText("date de fin de couverture: " + demande.getDate_fin());
        setStatusText();
    }
    private void setStatusText() {
        String statusText;
        Color color;

        switch (demande.getStatus()) {
            case 0:
                statusText = "Pending";
                color = Color.ORANGE;
                break;
            case 1:
                statusText = "Validated";
                color = Color.GREEN;
                break;
            case 2:
                statusText = "Rejected";
                color = Color.RED;
                break;
            default:
                statusText = "Unknown";
                color = Color.BLACK;
                break;
        }     com.setText(statusText);
       com.setTextFill(color);


    }
}
