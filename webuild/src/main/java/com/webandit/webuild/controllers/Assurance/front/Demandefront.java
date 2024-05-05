package com.webandit.webuild.controllers.Assurance.front;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.services.ServiceDemande;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Demandefront {
@FXML
private Button adddembtn;



@FXML
private TextField commentairetxt;

@FXML
private DatePicker ddebuttxt;
    @FXML
    private Button back;

@FXML
private DatePicker dfintxt;

@FXML
private TextField montanttxt;
    @FXML
    private TextField assuranceField;

    private String selectedAssuranceName;

    @FXML
    void addDemande(ActionEvent event) {

        // Get values from the input fields
        String montantStr = montanttxt.getText();

        Date debut = Date.valueOf(ddebuttxt.getValue());
        Date fin = Date.valueOf(dfintxt.getValue());
        String commentaire = commentairetxt.getText();


        // Convert montant from String to int
        int montant = Integer.parseInt(montantStr);

        // Parse debut and fin strings into Date objects

        String selectedAssu = assuranceField.getText();

        // Check if an assurance name is selected
        if (selectedAssu == null) {
            // Handle the case where no assurance is selected (show error message, etc.)
            return;
        }

        // Retrieve the assurance instance based on its name
        Assurance selectedAssurance = null;
        try {ServiceAssurance sa =new ServiceAssurance();
            List<Assurance> assurances = sa.selectAll();
            for (Assurance assurance : assurances) {
                if (assurance.getNom().equals(selectedAssu)) {
                    selectedAssurance = assurance;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Check if the selected assurance was found
        if (selectedAssurance == null) {
            // Handle the case where the selected assurance was not found (show error message, etc.)
            return;
        }

        // Set default values for user and status
        int defaultUser = 4;
        Integer defaultStatus = 0; // Using Integer instead of int to allow null values





        Demande demande = new Demande();
        demande.setA(selectedAssurance);
        demande.setMontant(montant);
        demande.setUser(defaultUser);
        demande.setDate_debut(debut);
        demande.setDate_fin(fin);
        demande.setCommentaire(commentaire);
        demande.setStatus(defaultStatus);


        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            // Use the service to add the demande
            serviceDemande.insertOne(demande);
            System.out.println("Demande ajoutée avec succès !");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }





    public void setSelectedAssuranceName(String selectedAssuranceName) {
        assuranceField.setText(selectedAssuranceName);
    }
    // This method will be called after the FXML file has been loaded
    public void initialize() {
        // Set the selected Assurance ID in the assuranceIdField
        assuranceField.setText(String.valueOf(selectedAssuranceName));
    }

    @FXML
    void goback(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}


