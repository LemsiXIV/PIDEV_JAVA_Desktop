package com.webandit.webuild.controllers;
import com.webandit.webuild.models.Utilisateur;
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
import com.webandit.webuild.services.serviceUtilisateur;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
                SessionManagement.getInstance(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getPwd(), utilisateur.getAdresse(), utilisateur.getTelephone());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));
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
            } else {
                pwderr.setText("Your username or password are incorrect");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

