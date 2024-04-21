package com.webandit.webuild.controllers;


import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.services.ServiceDemande;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Backdemande {


    @FXML
    private TableColumn<Demande, String> colAssurance;

    @FXML
    private TableColumn<Demande, Integer> colMontant;

    @FXML
    private TableColumn<Demande, String> colDebut;

    @FXML
    private TableColumn<Demande, String> colFin;
    @FXML
    private ChoiceBox<String> assurancetxt;

    @FXML
    private Button adddembtn;


    @FXML
    private TableColumn<Demande, Integer> colStatut;

    @FXML
    private TableView<Demande> tabdem;
    @FXML
    private TextField commentairetxt;

    @FXML
    private TextField ddebuttxt;

    @FXML
    private Button deldembtn;

    @FXML
    private TextField dfintxt;

    @FXML
    private Button moddembtn;

    @FXML
    private TextField montanttxt;





    // Method to initialize the controller
    @FXML
    public void initialize() {
        try { ServiceAssurance sa =new ServiceAssurance();
            List<Assurance> assurances = sa.selectAll();
            populateAssuranceChoiceBox(assurances);
            showDemandes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch and display all demandes in the TableView
    private void showDemandes() throws SQLException {
        // Fetch all demandes from the database
        ServiceDemande  ServiceDemande= new ServiceDemande();
        List<Demande> demandes = ServiceDemande.selectAll();

        // Convert the list to an ObservableList
        ObservableList<Demande> demandeList = FXCollections.observableArrayList(demandes);

        // Bind the ObservableList to the TableView
        tabdem.setItems(demandeList);

        colAssurance.setCellValueFactory(cellData -> {
            // Access the Assurance object associated with the Demande
            Assurance assurance = cellData.getValue().getA();
            if (assurance != null) {
                // Return the name of the assurance
                return new SimpleStringProperty(assurance.getNom());
            } else {
                // If assurance is null, return an empty string
                return new SimpleStringProperty("");
            }
        });
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

        colStatut.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    @FXML
    void addDemande(ActionEvent event) {

            // Get values from the input fields
        String montantStr = montanttxt.getText();
        String debutStr = ddebuttxt.getText();
        String finStr = dfintxt.getText();
        String commentaire = commentairetxt.getText();


        // Convert montant from String to int
        int montant = Integer.parseInt(montantStr);

        // Parse debut and fin strings into Date objects
        Date debut = Date.valueOf(debutStr);
        Date fin = Date.valueOf(finStr);

        String selectedAssuranceName = assurancetxt.getValue();

        // Check if an assurance name is selected
        if (selectedAssuranceName == null) {
            // Handle the case where no assurance is selected (show error message, etc.)
            return;
        }

        // Retrieve the assurance instance based on its name
        Assurance selectedAssurance = null;
        try {ServiceAssurance sa =new ServiceAssurance();
        List<Assurance> assurances = sa.selectAll();
            for (Assurance assurance : assurances) {
                if (assurance.getNom().equals(selectedAssuranceName)) {
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
            int defaultUser = 1;
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
                showDemandes();
                // Clear the input fields after adding the demande
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the error appropriately (e.g., show an error message to the user)
            }
        }


    private void clearFields() {
        montanttxt.clear();
        ddebuttxt.clear();
        dfintxt.clear();
        commentairetxt.clear();
    }
    private void populateAssuranceChoiceBox(List<Assurance> assurances) {
        ObservableList<String> assuranceNames = FXCollections.observableArrayList();
        for (Assurance assurance : assurances) {
            assuranceNames.add(assurance.getNom());
        }
        assurancetxt.setItems(assuranceNames);

    }
    @FXML
    void deldem(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            return;
        }

        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            // Use the service to delete the demande
            serviceDemande.deleteOne(selectedDemande);
            System.out.println("Demande supprimée avec succès !");
            // Refresh the TableView after deletion
            showDemandes();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }

    @FXML
    void getdatadem(MouseEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande != null) {
            // Set the data of the selected demande to the input fields
            montanttxt.setText(String.valueOf(selectedDemande.getMontant()));
            ddebuttxt.setText(selectedDemande.getDate_debut().toString());
            dfintxt.setText(selectedDemande.getDate_fin().toString());
            commentairetxt.setText(selectedDemande.getCommentaire());

            // Set the selected assurance name in the ChoiceBox
            String selectedAssuranceName = selectedDemande.getA().getNom();
            assurancetxt.setValue(selectedAssuranceName);
        }
    }

    @FXML
    void moddem(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            return;
        }

        // Get values from the input fields
        String montantStr = montanttxt.getText();
        String debutStr = ddebuttxt.getText();
        String finStr = dfintxt.getText();
        String commentaire = commentairetxt.getText();
        String selectedAssuranceName = assurancetxt.getValue(); // Get the selected assurance from the ChoiceBox

        // Convert montant from String to int
        int montant = Integer.parseInt(montantStr);

        // Parse debut and fin strings into Date objects
        Date debut = Date.valueOf(debutStr);
        Date fin = Date.valueOf(finStr);

        // Check if an assurance name is selected
        if (selectedAssuranceName == null) {
            // Handle the case where no assurance is selected (show error message, etc.)
            return;
        }

        // Retrieve the assurance instance based on its name
        Assurance selectedAssurance = null;
        try {
            ServiceAssurance sa = new ServiceAssurance();
            List<Assurance> assurances = sa.selectAll();
            for (Assurance assurance : assurances) {
                if (assurance.getNom().equals(selectedAssuranceName)) {
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

        // Set the modified values to the selected demande
        selectedDemande.setA(selectedAssurance);
        selectedDemande.setMontant(montant);
        selectedDemande.setDate_debut(debut);
        selectedDemande.setDate_fin(fin);
        selectedDemande.setCommentaire(commentaire);

        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            // Use the service to update the demande in the database
            serviceDemande.updateOne(selectedDemande);
            System.out.println("Demande modifiée avec succès !");
            // Refresh the TableView after modification
            showDemandes();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }


}






