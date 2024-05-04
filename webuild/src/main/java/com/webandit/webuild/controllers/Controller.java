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
    private String loggedInUserEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    exit.setOnMouseClicked(e->{
        System.exit(0);
    });
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
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void Assurence(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Assurence.fxml"));
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
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Forum.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void Utilisateur(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Utilisateur.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
    public void Signup(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void Login(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

    }
    public void ResetPwd(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Reset-pwd.fxml"));

    }
}
