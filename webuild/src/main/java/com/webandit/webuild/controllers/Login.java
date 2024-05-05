package com.webandit.webuild.controllers;
import com.webandit.webuild.models.Utilisateur;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
public class Login {

    @FXML
    private TextField emailtxt;

    @FXML
    private PasswordField pwdtxt;
    @FXML
    private Button LoginButton;
    @FXML
    private Hyperlink signinlink;
    @FXML
    private Label emailerr;
    @FXML
    private Label pwderr;
    @FXML
    private TextField pwdtxtShow;
    @FXML
    private ImageView openEyeIcon;
    @FXML
    private ImageView closeEyeIcon;
    @FXML
    private Hyperlink forgotLink;
    @FXML
    private Label exit;
    private final serviceUtilisateur serviceutilisater = new serviceUtilisateur();
    String pwd;
    public void initialize(){
        pwdtxtShow.setVisible(false);
        openEyeIcon.setVisible(false);

    }
    @FXML
    void Close_Eye(MouseEvent event) {
        pwdtxtShow.setVisible(true);
        openEyeIcon.setVisible(true);
        closeEyeIcon.setVisible(false);
        pwdtxt.setVisible(false);
    }
    @FXML
    void Open_Eye(MouseEvent event) {
        pwdtxtShow.setVisible(false);
        openEyeIcon.setVisible(false);
        closeEyeIcon.setVisible(true);
        pwdtxt.setVisible(true);
    }


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
        try {
            Utilisateur utilisateur;
            if ((utilisateur = serviceutilisater.Login(emailtxt.getText(), pwdtxt.getText())) != null) {
                SessionManagement.getInstance(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone(), utilisateur.getAdresse(), utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getRoles(), utilisateur.getIs_verified());
                System.out.println("User ID: " + SessionManagement.getInstance().getId());
                System.out.println("User Nom: " + SessionManagement.getInstance().getNom());
                System.out.println("User Prenom: " + SessionManagement.getInstance().getPrenom());
                System.out.println("User Email: " + SessionManagement.getInstance().getEmail());
                System.out.println("User Adresse: " + SessionManagement.getInstance().getAdresse());
                System.out.println("User Telephone: " + SessionManagement.getInstance().getTelephone());
                System.out.println("User Role: " + SessionManagement.getInstance().getRoles());
                System.out.println("User status: " + SessionManagement.getInstance().getIs_verified());
                if (utilisateur.getRoles().equals("Admin")){
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) LoginButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de saisie");
                        alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                        alert.show();
                    }
                }else{


                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/front/hello-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) LoginButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de saisie");
                        alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                        alert.show();

                    }}
            }else {
                pwderr.setText("Your username or password are incorrect");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeToSignIn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) signinlink.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Display error message using Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors du chargement de la page de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du chargement de la page de connexion. Veuillez réessayer.");
            alert.showAndWait();
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }


    @FXML
    void hidePassword(KeyEvent event) {
        pwd=pwdtxt.getText();
        pwdtxtShow.setText(pwd);
    }

    @FXML
    void showPassword(KeyEvent event) {
        pwd=pwdtxtShow.getText();
        pwdtxt.setText(pwd);
    }
    @FXML
    void changeToReset(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reset-pwd.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) forgotLink.getScene().getWindow();
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
