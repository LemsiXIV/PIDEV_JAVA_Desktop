package com.webandit.webuild.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    private Label exit;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (IOException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    public void home(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void project(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/front/front_project.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void Assurence(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Assurance/assurancefront.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Condidatures(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/offre/FrontCandidature.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void Offres(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/offre/FrontOffres.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Materiel(ActionEvent actionEvent) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Location.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Forum(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/forum/gridpost.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Utilisateur(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/front/UserProfil.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void calendri(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/front/calander.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }




}

