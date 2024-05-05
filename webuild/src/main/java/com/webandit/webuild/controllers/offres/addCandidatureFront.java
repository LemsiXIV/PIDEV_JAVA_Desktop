package com.webandit.webuild.controllers.offres;

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
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class addCandidatureFront {


    @FXML
    private TextField descp;

    @FXML
    private TextField email;

    @FXML
    private Button fileBtn;

    @FXML
    private Label fileNameLabel;

    @FXML
    private TextField idclient;

    @FXML
    private ChoiceBox<String> offreChoiceBox;
    public String filePath;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    void ConfirmerCan(ActionEvent event) {
        try {
            // Retrieve values from UI controls
            String descpValue = descp.getText();
            String idClientValue = idclient.getText();
            String emailValue = email.getText();
            String selectedOffreTitle = offreChoiceBox.getValue();
            if (filePath == null) {
                showAlert("Erreur", "Aucun fichier sélectionné.");
                return;
            }

            // Validate input fields
            if (descpValue.isEmpty() || idClientValue.isEmpty() || emailValue.isEmpty() || selectedOffreTitle == null) {
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
            Candidature candidature = new Candidature(selectedOffre.getId(),selectedOffre,selectedOffreTitle,Integer.parseInt(idClientValue), descpValue, emailValue,filePath);

            // Call the method to insert the Candidature into the database
            ServiceCandidature serviceCandidature = new ServiceCandidature();
            serviceCandidature.insertOne(candidature);
            showAlert("Candidature ajoutée", "La candidature a été ajoutée avec succès.");

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
    private boolean isValidEmail(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
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
    @FXML
    void initialize() {

        // Populate the ChoiceBox with available offres titles
        if (offreChoiceBox != null) {
            populateOffresChoiceBox();
        }


    }
    @FXML
    String uploadCV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload CV");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            fileNameLabel.setText(fileName);
            // Process the selected file
            filePath = selectedFile.getAbsolutePath();
            return filePath;
        } else {
            fileNameLabel.setText("No file selected");
            return null; // Return null if no file is selected
        }
    }

}
