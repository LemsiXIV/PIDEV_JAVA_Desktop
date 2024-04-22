package com.webandit.webuild.controllers;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.webandit.webuild.controllers.Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

        @FXML
        private TextField emailtxt;

        @FXML
        private TextField pwdtxt;
        @FXML
        private Button LoginButton;
        @FXML
        private Hyperlink signinlink;
        @FXML
        private Label emailerr;
        @FXML
        private Label pwderr;
    private boolean validatorPassword() {
        String password = pwdtxt.getText();
        if (password.isEmpty()) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must not be empty");
            pwdtxt.requestFocus();
            return false;}
        else {
            return true;
        }}
    private boolean validatorEmail() {
        String email = emailtxt.getText();
        if (email.isEmpty()) {
            emailerr.setStyle("-fx-text-fill: red;");
            emailerr.setText("Email must not be empty");
            emailtxt.requestFocus();
            return false;}
        else{
            return true;
        }}
        @FXML
        void Login(ActionEvent event) {
           String email= emailtxt.getText();
           String password= pwdtxt.getText();
            if ( !validatorEmail() || !validatorPassword()  ) {
                return;
            }
            com.webandit.webuild.services.serviceUtilisateur sp = new serviceUtilisateur();

            try {
                String userExists = sp.Login(email);
                if (userExists==null){
                    //System.out.println(userExists);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setContentText("No user found with the email: " + email);
                    alert.show();
                }else {
                    if(password.equals(userExists)){
                        SessionManagement.getInstance().setLoggedInUserEmail(email);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) LoginButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Failed");
                        alert.setContentText("Incorrect password for the user: " + email);
                        alert.show();
                    }
                }
            } catch (SQLException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            }
        }
    @FXML
    void changeToSignIn(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signup.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) signinlink.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
        } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
        }

    }

    }


