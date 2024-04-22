package com.webandit.webuild.controllers;

import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.webandit.webuild.models.Utilisateur;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Signup {

        @FXML
        private TextField adressetxt;

        @FXML
        private TextField emailtxt;

        @FXML
        private TextField nomtxt;

        @FXML
        private TextField prenomtxt;

        @FXML
        private TextField pwdtxt;

        @FXML
        private TextField telephonetxt;
        @FXML
        private Button savebtn;
        @FXML
        private Label adresseerr;
        @FXML
        private Label emailerr;
        @FXML
        private Label prenomerr;
        @FXML
        private Label nomerr;
        @FXML
        private Label pwderr;
        @FXML
        private Label tlperr;

private boolean validatorNom(){
    String nom = nomtxt.getText();
    if (nom.isEmpty() ) {
        nomerr.setStyle("-fx-text-fill: red;");
        nomerr.setText("Nom must not be empty");
        nomtxt.requestFocus();
        return false;
    }else if (nom.length() > 50){
        nomerr.setStyle("-fx-text-fill: red;");
        nomerr.setText("Nom must be less than 50 caracter");
        nomtxt.requestFocus();
        return false;
    } else if (nom.length()<3) {
        nomerr.setStyle("-fx-text-fill: red;");
        nomerr.setText("Nom must be more than 3 caracters");
        nomtxt.requestFocus();
        return false;
    } else if (!nom.matches("^[a-zA-Z]+$")) {
        nomerr.setStyle("-fx-text-fill: red;");
        nomerr.setText("Nom must contains only caracters");
        nomtxt.requestFocus();
        return false;
    } else{
        return true;
    }
}
    private boolean validatorPrenom(){
        String prenom = prenomtxt.getText();
        if (prenom.isEmpty() ) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must not be empty");
            prenomtxt.requestFocus();
            return false;
        }else if (prenom.length() > 50){
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must be less than 50 caracter");
            prenomtxt.requestFocus();
            return false;
        } else if (prenom.length()<3) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must be more than 3 caracters");
            prenomtxt.requestFocus();
            return false;
        } else if (!prenom.matches("^[a-zA-Z]+$")) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must contains only caracters");
            prenomtxt.requestFocus();
            return false;
        } else{
            return true;
        }}
    private boolean validatorEmail() {
        String email = emailtxt.getText();
        if (email.isEmpty()) {
            emailerr.setStyle("-fx-text-fill: red;");
            emailerr.setText("Email must not be empty");
            emailtxt.requestFocus();
            return false;
        } else if (email.length() > 50) {
            emailerr.setStyle("-fx-text-fill: red;");
            emailerr.setText("Email must be less than 50 characters");
            emailtxt.requestFocus();
            return false;
        }else if (email.length()<3) {
                prenomerr.setStyle("-fx-text-fill: red;");
                prenomerr.setText("Email must be more than 3 caracters");
                prenomtxt.requestFocus();
                return false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            emailerr.setStyle("-fx-text-fill: red;");
            emailerr.setText("Invalid email format");
            emailtxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatorAdresse(){
        String adresse = adressetxt.getText();
        if (adresse.isEmpty() ) {
            adresseerr.setStyle("-fx-text-fill: red;");
            adresseerr.setText("Nom must not be empty");
            adresseerr.requestFocus();
            return false;}
        else{
            return true;}

        }
    private boolean validatorPassword() {
        String password = pwdtxt.getText();
        if (password.isEmpty()) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must not be empty");
            pwdtxt.requestFocus();
            return false;
        } else if (password.length() < 8) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must be at least 8 characters long");
            pwdtxt.requestFocus();
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must contain at least one lowercase letter");
            pwdtxt.requestFocus();
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must contain at least one uppercase letter");
            pwdtxt.requestFocus();
            return false;
        } else if (!password.matches(".*\\d.*")) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must contain at least one digit");
            pwdtxt.requestFocus();
            return false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            pwderr.setStyle("-fx-text-fill: red;");
            pwderr.setText("Password must contain at least one special character");
            pwdtxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatorTelephone() {
        String telephone = telephonetxt.getText();
        if (telephone.isEmpty()) {
            tlperr.setStyle("-fx-text-fill: red;");
            tlperr.setText("Telephone number must not be empty");
            telephonetxt.requestFocus();
            return false;
        } else if (!telephone.matches("\\d{8}")) {
            tlperr.setStyle("-fx-text-fill: red;");
            tlperr.setText("Telephone number must contain exactly 8 digits");
            telephonetxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    @FXML
        void addUtilisateur(ActionEvent event) {
            com.webandit.webuild.services.serviceUtilisateur sp = new serviceUtilisateur();
            if (!validatorNom() || !validatorPrenom() || !validatorAdresse() || !validatorEmail() || !validatorPassword() || !validatorTelephone() ) {
            return;}
            try {
                    String email = emailtxt.getText();
                    String userExists = sp.Login(email);

                    if(userExists!=null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("User Exists");
                        alert.setContentText("A user with the same email already exists.");
                        alert.show();
                    }else {
                    com.webandit.webuild.models.Utilisateur u = new Utilisateur( nomtxt.getText(), prenomtxt.getText(),Integer.parseInt(telephonetxt.getText()),  adressetxt.getText(),emailtxt.getText(), pwdtxt.getText());
                    sp.insertOne(u);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) savebtn.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();}
                }catch (SQLException | IOException e){
                    System.out.println("err" + e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                    alert.show();
                } /*catch (NumberFormatException e) {
                    System.out.println("err" + e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                    alert.show();

                }*/

        }

    }


