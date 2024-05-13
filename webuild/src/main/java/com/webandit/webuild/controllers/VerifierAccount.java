package com.webandit.webuild.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class VerifierAccount {

    @FXML
    private TextField code;

    @FXML
    private Button vérifier;

    private String codeG;
    @FXML
    private ImageView backButton;


    public String getCodeG() {
        return codeG;
    }

    public void setCodeG(String codeG) {
        this.codeG = codeG;
    }
    String emailCurrent=SessionManagement.getInstance().getEmail();
    @FXML
    void verif_account(ActionEvent event) throws SQLException {
        String entredCode= code.getText();
        if(entredCode.equals(codeG)){
            serviceUtilisateur sp=new serviceUtilisateur();
            sp.updateIsVerified(emailCurrent);
            showAlert(Alert.AlertType.INFORMATION, "Code Verified", "Verification code is correct. You can proceed.");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/front/hello-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) vérifier.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Verification Code", "The verification code you entered is invalid.");
        }

    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}






