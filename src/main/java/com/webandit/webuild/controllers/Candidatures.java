package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceCandidature;
import com.webandit.webuild.services.ServiceOffre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    private TableColumn<Candidature, String> colCv;

    @FXML
    private TextField comp;

    @FXML
    private TextField descp;

    @FXML
    private TextField idclient;

    @FXML
    private TextField email;

    @FXML
    private Button fileBtn;
    @FXML
    private Button clear;
    @FXML
    private Button addCan;
    @FXML
    private Button deleteCan;
    @FXML
    private Button mailBtn;
    @FXML
    private WebView webView;
    @FXML
    private Button selectCan;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Button updateCan;
    @FXML
    private ChoiceBox<String> offreChoiceBox; // Changed to String type

    @FXML
    private TableView<Candidature> tablecan;
    public String filePath;
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
        if (coloffre != null) {
            coloffre.setCellValueFactory(new PropertyValueFactory<>("offreTitle"));
        }
        if (coldescrp != null) {
            coldescrp.setCellValueFactory(new PropertyValueFactory<>("description"));
        }
        if (colcomp != null) {
            colcomp.setCellValueFactory(new PropertyValueFactory<>("competences"));
        }
        if (colemail != null) {
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        }
        if (colCv != null) {
            colCv.setCellValueFactory(new PropertyValueFactory<>("cv"));
        }

        // Populate the ChoiceBox with available offres titles
        if (offreChoiceBox != null) {
            populateOffresChoiceBox();
        }

        // Handle ChoiceBox selection
        if (offreChoiceBox != null) {
            offreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        updateTableViewWithCandidaturesForOffre(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        if (tablecan != null) {
            afficherCandidature();
        }
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
                if (filePath == null) {
                    showAlert("Erreur", "Aucun fichier sélectionné.");
                    return;
                }

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
                Candidature candidature = new Candidature(selectedOffre.getId(),selectedOffre,selectedOffreTitle,Integer.parseInt(idClientValue), descpValue,compValue, emailValue,filePath);

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

    //i actually dont know
    private FrontCandidature frontController; // Reference to the task_front controller
    public void setFrontController(FrontCandidature frontController) {
        this.frontController = frontController;
    }


    @FXML
    void SendingMails(ActionEvent event) throws IOException {
// Load the EmailForm.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmailConfirmation.fxml"));
        Parent root = fxmlLoader.load();

        // Create a new stage for the email form
        Stage stage = new Stage();
        stage.setTitle("Email Form");
        stage.setScene(new Scene(root));

        // Show the email form window
        stage.show();
    }
    @FXML
    public void displayPDF(ActionEvent event) {
        Candidature selectedCandidature = tablecan.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            String pdfURL = "file:///C:/Users/Malek/Downloads/Assistant%20visa%20-%20France-Visas.pdf"; // Assuming getCv() returns the URL of the PDF file

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pdf.fxml"));
                Parent root = loader.load();
                pdf controller = loader.getController();
                controller.loadPDF(pdfURL);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error loading WebView-only view
            }
        } else {
            // Handle case where no candidature is selected
        }
    }

}
