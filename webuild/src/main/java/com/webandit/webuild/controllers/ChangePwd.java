package com.webandit.webuild.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

public class ChangePwd {

    @FXML
    private Label comfirmerr;
    @FXML
    private Button newPWD;

    @FXML
    private PasswordField confirmNvPwd;

    @FXML
    private PasswordField nvPassw;

    @FXML
    private Label nvpwderr;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void changerPwd(ActionEvent event) {
        String nvPwd = nvPassw.getText();
        String confirmPwd = confirmNvPwd.getText();

        if (!nvPwd.equals(confirmPwd)) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Passwords do not match");
            nvpwderr.requestFocus();
            return;
        }
        com.webandit.webuild.services.serviceUtilisateur sp = new serviceUtilisateur();
        try{
            char[] bcryptChars = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(6, nvPassw.getText().toCharArray());
            System.out.println(bcryptChars);
            String bcryptString = new String(bcryptChars);
            sp.updatePassword(email,bcryptString);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Updated");
            alert.setContentText("Password Updated");
            alert.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) newPWD.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
                /*Alert aler = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();*/
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
