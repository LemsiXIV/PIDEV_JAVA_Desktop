package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceCandidature;
import com.webandit.webuild.services.ServiceOffre;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class Offres {

    ServiceOffre ps = new ServiceOffre();

    @FXML
    private Button btnSwitch;

    @FXML
    private Button btnclear;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TextArea descrp;

    @FXML
    private TextField tit;

    @FXML
    private TextField lati;

    @FXML
    private TextField longi;

    @FXML
    private TextField salary;
    @FXML
    private TableColumn<Offre, String> colDescrp;

    @FXML
    private TableColumn<Offre, Float> colLati;

    @FXML
    private TableColumn<Offre, Float> colLongi;

    @FXML
    private TableColumn<Offre, Float> colSalary;

    @FXML
    private TableColumn<Offre, String> colTitle;

    @FXML
    private TableView<Offre> table;



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    public void afficherOffre() {
        try {
            table.getItems().setAll(ps.selectAll()); // Accessing ServiceOffre methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des Offres!");
        }
    }
    @FXML
    void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colDescrp.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colLati.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        colLongi.setCellValueFactory(new PropertyValueFactory<>("Longitude"));
        afficherOffre(); //refrech el tableview
    }
    @FXML
    void clearField(ActionEvent event) {
        tit.clear();
        descrp.clear();
        salary.clear();
        lati.clear();
        longi.clear();
    }

    @FXML
    void createOffre(ActionEvent event) {
        try {
            // Retrieve values from UI controls
            String title = tit.getText();
            String description = descrp.getText();
            String salaryText = salary.getText();
            String latiText = lati.getText();
            String longiText = longi.getText();

            // Check if the title meets the constraints
            if (!isValidTitle(title)) {
                showAlert("Erreur de saisie", "Le titre doit contenir uniquement des lettres et être d'au moins 4 caractères.");
                return; // Exit the method if the title is invalid
            }
            // Check if salary, latitude, and longitude are valid numeric values
            if (!isValidNumericValue(salaryText) || !isValidNumericValue(latiText) || !isValidNumericValue(longiText)) {
                showAlert("Erreur de saisie", "Veuillez saisir des valeurs numériques valides pour le salaire, la latitude et la longitude.");
                return; // Exit the method if any value is invalid
            }
            // Parse salary, lati, and longi as floats
            float salary = Float.parseFloat(salaryText);
            BigDecimal latitude = new BigDecimal(latiText);
            BigDecimal longitude = new BigDecimal(longiText);

            // Create a new Offre object
            Offre offre = new Offre(title, description, salary, latitude, longitude);

            // Call the method to insert the Offre into the database
            ServiceOffre serviceOffre = new ServiceOffre();
            serviceOffre.insertOne(offre);

            // Refresh the table after adding an Offre
            afficherOffre();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir des valeurs numériques valides pour le salaire, la latitude et la longitude.");
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout de l'offre à la base de données. Veuillez réessayer plus tard ou contacter l'administrateur.");
            // Log the SQL exception for further analysis
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite. Veuillez réessayer ou contacter l'administrateur.");
            // Log any other unexpected exceptions
            e.printStackTrace();
        }
    }



    @FXML
    void deleteOffre(ActionEvent event) {
        Offre selectedOffre = table.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            try {
                // Get the ID of the selected offre
                int offreId = selectedOffre.getId();

                // Call the deleteOne function from the service to delete the Offre from the database
                ServiceOffre serviceOffre = new ServiceOffre();
                serviceOffre.deleteOne(selectedOffre);
                // Delete associated candidatures
                ServiceCandidature serviceCandidature = new ServiceCandidature();
                serviceCandidature.deleteCandidatesByOfferId(offreId);

                // Refresh the table view after deleting
                afficherOffre();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression de l'offre!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune offre sélectionnée");
        }
    }
    @FXML
    void selectOffre(ActionEvent event) {
        Offre selectedOffre = table.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            // Set the values of UI controls with the selected Offre's properties
            tit.setText(selectedOffre.getTitle());
            descrp.setText(selectedOffre.getDescription());
            salary.setText(String.valueOf(selectedOffre.getSalary()));
            lati.setText(String.valueOf(selectedOffre.getLatitude()));
            longi.setText(String.valueOf(selectedOffre.getLongitude()));
        } else {
            showAlert("Aucune sélection", "Aucune offre sélectionnée");
        }
    }

    @FXML
    void updateOffre(ActionEvent event) {
        Offre selectedOffre = table.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            try {
                // Retrieve values from UI controls
                String title = tit.getText();
                String description = descrp.getText();
                String salaryText = salary.getText();
                String latiText = lati.getText();
                String longiText = longi.getText();

                // Check if the title meets the constraints
                if (!isValidTitle(title)) {
                    showAlert("Erreur de saisie", "Le titre doit contenir uniquement des lettres et être d'au moins 4 caractères.");
                    return; // Exit the method if the title is invalid
                }

                // Check if salary, latitude, and longitude are valid numeric values
                if (!isValidNumericValue(salaryText) || !isValidNumericValue(latiText) || !isValidNumericValue(longiText)) {
                    showAlert("Erreur de saisie", "Veuillez saisir des valeurs numériques valides pour le salaire, la latitude et la longitude.");
                    return; // Exit the method if any value is invalid
                }

                // Update the selected Offre object with the modified data
                selectedOffre.setTitle(tit.getText());
                selectedOffre.setDescription(descrp.getText());
                selectedOffre.setSalary(Float.parseFloat(salary.getText()));
                selectedOffre.setLatitude(new BigDecimal(lati.getText()));
                selectedOffre.setLongitude(new BigDecimal(longi.getText()));

                // Call the updateOne function from the service to update the database
                ServiceOffre serviceOffre = new ServiceOffre();
                serviceOffre.updateOne(selectedOffre);

                // Refresh the table view after updating
                afficherOffre();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour de l'offre!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune offre sélectionnée");
        }
    }

    private boolean isValidNumericValue(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidTitle(String title) {
        // Check if the title contains only letters and is longer than 4 characters
        return title.matches("[a-zA-Z\\s]+") && title.length() >= 4;
    }

   /* @FXML
    void switchToCandidatures(ActionEvent event) throws IOException  {
// Load Candidatures.fxml
        Parent candidaturesParent = FXMLLoader.load(getClass().getResource("/fxml/Candidatures.fxml"));
        Scene candidaturesScene = new Scene(candidaturesParent);

        // Get the stage from the button
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(candidaturesScene);
        stage.show();
    }*/



}
