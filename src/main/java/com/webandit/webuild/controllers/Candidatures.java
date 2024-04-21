package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceCandidature;
import com.webandit.webuild.services.ServiceOffre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class Candidatures {
    ServiceCandidature cn = new ServiceCandidature();

    @FXML
    private TableColumn<Candidature, String> colcomp;

    @FXML
    private TableColumn<Candidature, String> coldescrp;

    @FXML
    private TableColumn<Candidature, String> colemail;

    @FXML
    private TableColumn<Candidature, String> coloffre;

    @FXML
    private TextField comp;

    @FXML
    private TextField descp;

    @FXML
    private TextField idclient;

    @FXML
    private TextField email;

    @FXML
    private Button clear;
    @FXML
    private Button addCan;
    @FXML
    private Button deleteCan;

    @FXML
    private Button selectCan;

    @FXML
    private Button updateCan;
    @FXML
    private ChoiceBox<String> offreChoiceBox; // Changed to String type

    @FXML
    private TableView<Candidature> tablecan;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void afficherCandidature() {
        try {
            tablecan.getItems().setAll(cn.selectAll());
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des candidatures!");
        }
    }

    @FXML
    void initialize() {
        // Set up column cell value factories
        coloffre.setCellValueFactory(new PropertyValueFactory<>("offreTitle"));
        coldescrp.setCellValueFactory(new PropertyValueFactory<>("description"));
        colcomp.setCellValueFactory(new PropertyValueFactory<>("competences"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Populate the ChoiceBox with available offres titles
        populateOffresChoiceBox();

        // Handle ChoiceBox selection
        offreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    updateTableViewWithCandidaturesForOffre(newValue);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Refresh the table view
        afficherCandidature();
    }

    @FXML
    void clearField(ActionEvent event) {
        idclient.clear();
        descp.clear();
        comp.clear();
        email.clear();
        offreChoiceBox.getItems().clear();
    }

    private void populateOffresChoiceBox() {
        try {
            ServiceOffre serviceOffre = new ServiceOffre();
            List<Offre> offres = serviceOffre.selectAll();
            ObservableList<String> offreTitles = FXCollections.observableArrayList();
            for (Offre offre : offres) {
                offreTitles.add(offre.getTitle());
            }
            offreChoiceBox.setItems(offreTitles);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des offres!");
        }
    }

    private void updateTableViewWithCandidaturesForOffre(String selectedOffreTitle) throws SQLException {
        List<Candidature> candidaturesForOffre = cn.getCandidaturesByOffreTitle(selectedOffreTitle);
        tablecan.getItems().setAll(candidaturesForOffre);
    }
    @FXML
    void addCandidature(ActionEvent event) {
            try {
                // Retrieve values from UI controls
                String compValue = comp.getText();
                String descpValue = descp.getText();
                String idClientValue = idclient.getText();
                String emailValue = email.getText();
                String selectedOffreTitle = offreChoiceBox.getValue();

                // Validate input fields
                if (compValue.isEmpty() || descpValue.isEmpty() || idClientValue.isEmpty() || emailValue.isEmpty() || selectedOffreTitle == null) {
                    showAlert("Erreur de saisie", "Veuillez remplir tous les champs et sélectionner une offre.");
                    return; // Exit the method if any field is empty or no offre is selected
                }
                // Validate description length
                if (descpValue.length() <= 14) {
                    showAlert("Erreur de saisie", "La description doit contenir plus de 14 caractères.");
                    return; // Exit the method if description length is too short
                }
                // Validate email format using regular expression
                if (!isValidEmail(emailValue)) {
                    showAlert("Erreur de saisie", "Veuillez saisir une adresse e-mail valide.");
                    return; // Exit the method if email is invalid
                }
                ServiceOffre serviceOffre = new ServiceOffre();
                Offre selectedOffre = serviceOffre.getOffreByTitle(selectedOffreTitle);
                // Create a new Candidature object
                Candidature candidature = new Candidature(selectedOffre.getId(),selectedOffre,selectedOffreTitle,Integer.parseInt(idClientValue), descpValue,compValue, emailValue);

                // Call the method to insert the Candidature into the database
                ServiceCandidature serviceCandidature = new ServiceCandidature();
                serviceCandidature.insertOne(candidature);

                // Refresh the table after adding a Candidature
                afficherCandidature();
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Veuillez saisir une valeur numérique valide pour l'ID du client.");
            } catch (SQLException e) {
                showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout de la candidature à la base de données. Veuillez réessayer plus tard ou contacter l'administrateur.");
                // Log the SQL exception for further analysis
                e.printStackTrace();
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur inattendue s'est produite. Veuillez réessayer ou contacter l'administrateur.");
                // Log any other unexpected exceptions
                e.printStackTrace();
            }
        }



    @FXML
    void deleteCandidature(ActionEvent event) {
        Candidature selectedCandidature = tablecan.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            try {
                // Call the deleteOne method from the ServiceCandidature to delete the Candidature from the database
                ServiceCandidature serviceCandidature = new ServiceCandidature();
                serviceCandidature.deleteOne(selectedCandidature);

                // Refresh the table view after deleting
                afficherCandidature();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression de la candidature!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune candidature sélectionnée");
        }
    }


    @FXML
    void selectCandidature(ActionEvent event) {
        Candidature selectedCandidature = tablecan.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            // Set the values of UI controls with the selected Candidature's properties
            idclient.setText(String.valueOf(selectedCandidature.getId_client()));
            descp.setText(selectedCandidature.getDescription());
            comp.setText(selectedCandidature.getCompetences());
            email.setText(selectedCandidature.getEmail());

            // Assuming offrelist is a ChoiceBox<Offre> for selecting offres,
            // you can set the selected item based on the Candidature's associated Offre
            Offre associatedOffre = selectedCandidature.getOffre();
            if (associatedOffre != null) {
                offreChoiceBox.setValue(associatedOffre.getTitle());
            } else {
                offreChoiceBox.getSelectionModel().clearSelection();
            }
        } else {
            showAlert("Aucune sélection", "Aucune candidature sélectionnée");
        }
    }


    @FXML
    void updateCandidature(ActionEvent event) {
        Candidature selectedCandidature = tablecan.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            try {
                // Retrieve values from UI controls
                String compValue = comp.getText();
                String descpValue = descp.getText();
                String emailValue = email.getText();

                // Update the selected Candidature object with the modified data
                selectedCandidature.setCompetences(compValue);
                selectedCandidature.setDescription(descpValue);
                selectedCandidature.setEmail(emailValue);

                // Call the updateOne method from the ServiceCandidature to update the database
                ServiceCandidature serviceCandidature = new ServiceCandidature();
                serviceCandidature.updateOne(selectedCandidature);

                // Refresh the table view after updating
                afficherCandidature();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour de la candidature!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune candidature sélectionnée");
        }
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    @FXML
    void clear(ActionEvent event) {
        comp.clear();
        descp.clear();
        idclient.clear();
        email.clear();
    }
}
