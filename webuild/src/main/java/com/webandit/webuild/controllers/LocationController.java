package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Location;
import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.services.ServiceMateriel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LocationController implements Initializable {

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayMateriels();
    }

    private void displayMateriels() {
        ServiceMateriel serviceMateriel = new ServiceMateriel();
        List<Materiel> materiels;
        try {
            materiels = serviceMateriel.selectAll();
            int column = 0;
            int row = 0;
            for (Materiel materiel : materiels) {
                if (column == 3) { // Maximum 3 materiels per row
                    column = 0;
                    row++;
                }
                grid.add(createMaterielPane(materiel), column++, row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private VBox createMaterielPane(Materiel materiel) {
        // Materiel Container
        VBox materielContainer = new VBox();
        materielContainer.getStyleClass().add("materiel-container");
        materielContainer.setAlignment(Pos.TOP_CENTER);
        materielContainer.setPadding(new Insets(10));
        materielContainer.setSpacing(10);
        materielContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        materielContainer.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(3.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.LIGHTGRAY);
        materielContainer.setEffect(dropShadow);

        // Image
        ImageView imageView = new ImageView(new Image(new File(materiel.getImage()).toURI().toString()));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Labels Container
        VBox labelsContainer = new VBox();
        labelsContainer.setAlignment(Pos.CENTER_LEFT);
        labelsContainer.setSpacing(5);

        // Libelle
        Label libelleLabel = new Label("Name: " + materiel.getLibelle());
        libelleLabel.getStyleClass().add("materiel-label");

        // Description
        Label descriptionLabel = new Label("Description: " + materiel.getDescription());
        descriptionLabel.getStyleClass().add("materiel-label");
        descriptionLabel.setWrapText(true); // Enable text wrapping
        descriptionLabel.setMaxWidth(200); // Set maximum width for description label

        // Prix
        Label prixLabel = new Label("Price: " + String.valueOf(materiel.getPrix()) + "DT");
        prixLabel.getStyleClass().add("materiel-label");

        // Add labels to the labels container
        labelsContainer.getChildren().addAll(libelleLabel, descriptionLabel, prixLabel);

        // Louer button
        Button louerButton = new Button("Louer");
        louerButton.setMaxWidth(Double.MAX_VALUE);
        louerButton.setOnAction(event -> handleLouer(materiel)); // Set action handler

        // Add elements to the materiel container
        materielContainer.getChildren().addAll(imageView, labelsContainer, louerButton);

        // Set VBox alignment to bottom-center
        VBox.setVgrow(labelsContainer, Priority.ALWAYS);
        VBox.setVgrow(louerButton, Priority.ALWAYS);
        VBox.setMargin(louerButton, new Insets(10, 0, 0, 0)); // Add margin to the louer button

        return materielContainer;
    }


    private void handleLouer(Materiel materiel) {
        // Implement your logic for handling the "Louer" button action
        System.out.println("Handling Louer action for: " + materiel.getLibelle());
        // Here you can add your logic for what happens when the "Louer" button is clicked
    }
}
