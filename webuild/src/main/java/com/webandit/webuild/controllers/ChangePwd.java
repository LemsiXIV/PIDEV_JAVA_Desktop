package com.webandit.webuild.controllers;

import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private ImageView backButton;

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
        if ( !validatorPassword() ) {
            return;
        }
        if (  !nvPwd.equals(confirmPwd)) {
            comfirmerr.setStyle("-fx-text-fill: red;");
            comfirmerr.setText("Passwords do not match");
            comfirmerr.requestFocus();
            return;
        }
        com.webandit.webuild.services.serviceUtilisateur sp = new serviceUtilisateur();
        try{
            sp.updatePassword(email,nvPwd);
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
    private boolean validatorPassword() {
        String password = nvPassw.getText();
        if (password.isEmpty()) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must not be empty");
            nvpwderr.requestFocus();
            return false;
        } else if (password.length() < 8) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must be at least 8 characters long");
            nvpwderr.requestFocus();
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must contain at least one lowercase letter");
            nvpwderr.requestFocus();
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must contain at least one uppercase letter");
            nvpwderr.requestFocus();
            return false;
        } else if (!password.matches(".*\\d.*")) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must contain at least one digit");
            nvpwderr.requestFocus();
            return false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            nvpwderr.setStyle("-fx-text-fill: red;");
            nvpwderr.setText("Password must contain at least one special character");
            nvpwderr.requestFocus();
            return false;
        } else {
            return true;
        }
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}
