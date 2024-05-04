package com.webandit.webuild.controllers.Assurance.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField searchField;

    private final ServiceAssurance serviceAssurance = new ServiceAssurance();
   private String selectedAssuranceName;

    @FXML
    void initialize() {
        try {
            actualise();
            // Ajouter un écouteur d'événements au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        // Si le champ de recherche est vide, afficher tous les articles
                        actualise();
                    } else {
                        // Sinon, actualiser la liste des articles en fonction du terme de recherche
                        actualiseWithSearch(newValue);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
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
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void actualiseWithSearch(String searchTerm) throws SQLException {
        System.out.println("Search term: " + searchTerm); // Print the search term to verify it's being captured correctly

        grid.getChildren().clear();


        // Print the search results to check if they are being retrieved correctly



        List<Assurance> assurances = serviceAssurance.rechercherOffres(searchTerm);
        System.out.println("Search results: " + assurances);
        if (assurances.isEmpty()) {
            System.out.println("Aucun offre trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

        for (Assurance assurance : assurances) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cardAssu.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AssuranceCardController c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.setData(assurance);


                anchorPane.setOnMouseClicked(event -> handleCardClick(assurance));
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 2) {
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
    @FXML
    void   seedem(ActionEvent event) throws IOException {    Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/showDemandeFront.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();}

}
