package com.webandit.webuild.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controllerdash implements Initializable {
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
            Logger.getLogger(Controllerdash.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    public void home(ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void project(ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/Back/Project.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void Assurence(ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Assurance/Assurence.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Condidatures(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Condidatures.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Materiel(ActionEvent actionEvent) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Materiel.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Forum(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/forum/forumXml.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Utilisateur(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Utilisateur.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void back(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/front/front_project.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void demande(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Assurance/backdemande.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    void Client(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}

