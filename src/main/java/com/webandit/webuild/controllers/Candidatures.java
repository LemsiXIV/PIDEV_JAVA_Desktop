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
    private TableColumn<Candidature, Offre> coloffre;

    @FXML
    private TextField comp;

    @FXML
    private TextField descp;

    @FXML
    private TextField idclient;

    @FXML
    private ChoiceBox<?> offrelist;

    @FXML
    private TableView<?> tablecan;
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

}
