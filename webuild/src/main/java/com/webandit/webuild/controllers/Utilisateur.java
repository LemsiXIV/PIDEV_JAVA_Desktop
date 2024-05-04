package com.webandit.webuild.controllers;

import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {

    @FXML
    private TextField adressetxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private TextField nomtxt;

    @FXML
    private TextField prenomtxt;

    @FXML
    private TextField tlptxt;
    @FXML
    private Label adresseerr;
    @FXML
    private Label emailerr;
    @FXML
    private Label nomerr;
    @FXML
    private Label prenomerr;
    @FXML
    private Label tlperr;
    @FXML
    private Button delete;

    private boolean validatorNom() {
        String nom = nomtxt.getText();
        if (nom.isEmpty()) {
            nomerr.setStyle("-fx-text-fill: red;");
            nomerr.setText("Nom must not be empty");
            nomtxt.requestFocus();
            return false;
        } else if (nom.length() > 50) {
            nomerr.setStyle("-fx-text-fill: red;");
            nomerr.setText("Nom must be less than 50 caracter");
            nomtxt.requestFocus();
            return false;
        } else if (nom.length() < 3) {
            nomerr.setStyle("-fx-text-fill: red;");
            nomerr.setText("Nom must be more than 3 caracters");
            nomtxt.requestFocus();
            return false;
        } else if (!nom.matches("^[a-zA-Z]+$")) {
            nomerr.setStyle("-fx-text-fill: red;");
            nomerr.setText("Nom must contains only caracters");
            nomtxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatorPrenom() {
        String prenom = prenomtxt.getText();
        if (prenom.isEmpty()) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must not be empty");
            prenomtxt.requestFocus();
            return false;
        } else if (prenom.length() > 50) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must be less than 50 caracter");
            prenomtxt.requestFocus();
            return false;
        } else if (prenom.length() < 3) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must be more than 3 caracters");
            prenomtxt.requestFocus();
            return false;
        } else if (!prenom.matches("^[a-zA-Z]+$")) {
            prenomerr.setStyle("-fx-text-fill: red;");
            prenomerr.setText("Prenom must contains only caracters");
            prenomtxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }

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
        } else if (email.length() < 3) {
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

    private boolean validatorAdresse() {
        String adresse = adressetxt.getText();
        if (adresse.isEmpty()) {
            adresseerr.setStyle("-fx-text-fill: red;");
            adresseerr.setText("Nom must not be empty");
            adresseerr.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private boolean validatorTelephone() {
        String telephone = tlptxt.getText();
        if (telephone.isEmpty()) {
            tlperr.setStyle("-fx-text-fill: red;");
            tlperr.setText("Telephone number must not be empty");
            tlptxt.requestFocus();
            return false;
        } else if (!telephone.matches("\\d{8}")) {
            tlperr.setStyle("-fx-text-fill: red;");
            tlperr.setText("Telephone number must contain exactly 8 digits");
            tlptxt.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    void updateUtilisateur(ActionEvent event) {
        if (!validatorNom() || !validatorPrenom() || !validatorAdresse() || !validatorEmail() || !validatorTelephone()) {
            return;
        }
        com.webandit.webuild.models.Utilisateur u = new com.webandit.webuild.models.Utilisateur(nomtxt.getText(), prenomtxt.getText(), Integer.parseInt(tlptxt.getText()), adressetxt.getText(), emailtxt.getText());
        com.webandit.webuild.services.serviceUtilisateur sp = new serviceUtilisateur();
        try {
            sp.updateOne(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("updated");
            alert.setHeaderText(null);
            alert.setContentText("update successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while retrieving user information."+e);
            alert.showAndWait();
            */
        }

    }

    private serviceUtilisateur sp = new serviceUtilisateur();

    public void initialize() {
        nomtxt.setText(SessionManagement.getInstance().getNom());
        prenomtxt.setText(SessionManagement.getInstance().getPrenom());
        emailtxt.setText(SessionManagement.getInstance().getEmail());
        adressetxt.setText(SessionManagement.getInstance().getAdresse());
        tlptxt.setText(String.valueOf(SessionManagement.getInstance().getTelephone()));
    }


    @FXML
    void deleteUser(ActionEvent event) {
        com.webandit.webuild.models.Utilisateur u = new com.webandit.webuild.models.Utilisateur(nomtxt.getText(), prenomtxt.getText(), Integer.parseInt(tlptxt.getText()), adressetxt.getText(), emailtxt.getText());

        try {
            sp.deleteOne(u);
            SessionManagement.getInstance().cleanUserSession();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}