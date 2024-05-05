package com.webandit.webuild.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.webandit.webuild.models.Offre;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image("file:/resources/image/malek.jpg");
        img.setImage(image);
    }
    public void getData(Offre offre) {
        this.offre = offre;
        titre.setText(offre.getTitle());
        descrp.setText(offre.getDescription());
        salary.setText(String.valueOf(offre.getSalary()));


    }
    @FXML
    void Postuler(ActionEvent event) {
        try {
            // Load the Candidatures.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCandidatureFront.fxml"));
            Parent candidaturesRoot = loader.load();

            // Create a new stage for the Candidatures scene
            Stage candidaturesStage = new Stage();
            candidaturesStage.setScene(new Scene(candidaturesRoot));
            candidaturesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

}
