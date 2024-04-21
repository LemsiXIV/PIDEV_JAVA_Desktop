package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceCandidature;

import com.webandit.webuild.services.ServiceOffre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

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
    private ChoiceBox<Offre> offrelist;

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
            tablecan.getItems().setAll(cn.selectAll()); // Accessing ServiceOffre methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des Offres!");
        }
    }
    @FXML
    void initialize() {
        coloffre.setCellValueFactory(new PropertyValueFactory<>("offreTitle"));
        coldescrp.setCellValueFactory(new PropertyValueFactory<>("description"));
        colcomp.setCellValueFactory(new PropertyValueFactory<>("competences"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        afficherCandidature(); //refrech el tableview
    }
    @FXML
    void clearField(ActionEvent event) {
        idclient.clear();
        descp.clear();
        comp.clear();
        email.clear();
        offrelist.getItems().clear();
    }

}
