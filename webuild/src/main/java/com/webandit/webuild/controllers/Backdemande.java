package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceDemande;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Demande, Integer> colStatut;

    @FXML
    private TableView<Demande> tabdem;

    // Method to initialize the controller
    @FXML
    public void initialize() {
        try {
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

}
