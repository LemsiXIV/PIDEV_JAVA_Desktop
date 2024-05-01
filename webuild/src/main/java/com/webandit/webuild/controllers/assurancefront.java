package com.webandit.webuild.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.services.ServiceAssurance;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class assurancefront {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private final ServiceAssurance serviceAssurance = new ServiceAssurance();
   private String selectedAssuranceName;

    @FXML
    void initialize() {
        try {
            actualise();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualise() throws SQLException {
        // Clear current elements from the grid
        grid.getChildren().clear();

        List<Assurance> assurances = serviceAssurance.selectAll();
        if (assurances.isEmpty()) {
            System.out.println("The assurance list is empty.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Assurance assurance : assurances) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cardAssu.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AssuranceCardController controller = fxmlLoader.getController();
                if (controller == null) {
                    System.out.println("The controller for the item was not initialized.");
                    continue;
                }

                controller.setData(assurance);

                // Add event handler to handle card click
                anchorPane.setOnMouseClicked(event -> handleCardClick(assurance));

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCardClick(Assurance assurance) { // Handle click event for the assurance card
        System.out.println("Assurance clicked: " + assurance.getNom());
        selectedAssuranceName = assurance.getNom(); // Store the selected assurance name
    }

    @FXML
    void addchoice(ActionEvent event) throws IOException {
        // Use the selected assurance name here

        if (selectedAssuranceName != null) {
            // Load the demandefront.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/demandefront.fxml"));
            Parent demandefrontParent = loader.load();

            // Pass the selected assurance's name to the demandefront controller
            Demandefront demandefrontController = loader.getController();
            demandefrontController.setSelectedAssuranceName(selectedAssuranceName);

            // Set up the scene and stage
            Scene demandefrontScene = new Scene(demandefrontParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(demandefrontScene);
            window.show();
        } else {
            // Handle case where no assurance is selected
            System.out.println("Please select an assurance before proceeding.");
        }
    }
}
