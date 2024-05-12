package com.webandit.webuild.controllers.back;

import com.webandit.webuild.models.User;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class AddUser {

    @FXML
    private Label adresseerr;

    @FXML
    private TextField adressetxt;

    @FXML
    private ImageView closeEyeIcon;

    @FXML
    private Label emailerr;

    @FXML
    private TextField emailtxt;

    @FXML
    private Label nomerr;

    @FXML
    private TextField nomtxt;

    @FXML
    private ImageView openEyeIcon;

    @FXML
    private Label prenomerr;

    @FXML
    private TextField prenomtxt;

    @FXML
    private Label pwderr;

    @FXML
    private PasswordField pwdtxt;

    @FXML
    private TextField pwdtxtshow;

    @FXML
    private Button savebtn;

    @FXML
    private TextField telephonetxt;

    @FXML
    private Label tlperr;
    private String pwd;
    @FXML
    private ImageView backButton;
    @FXML
    private TextField biotxt;

    @FXML
    private TextField cintxt;
    @FXML
    private DatePicker datetxt;
    LocalDate localDate = datetxt.getValue();
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    @FXML
    private TextField fonctiontxt;

    @FXML
    void addUtilisateur(ActionEvent event) throws SQLException, IOException {
        serviceUtilisateur sp = new serviceUtilisateur();
        User u = new User(emailtxt.getText(), pwdtxt.getText(),nomtxt.getText(), prenomtxt.getText(), telephonetxt.getText(),cintxt.getText(),fonctiontxt.getText(), adressetxt.getText(),date, biotxt.getText(),  Arrays.asList("ROLE_USER"), 0,1);
        sp.insertOne(u);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) savebtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void close_Eye(MouseEvent event) {
        pwdtxtshow.setVisible(false);
        openEyeIcon.setVisible(false);
        closeEyeIcon.setVisible(true);
        pwdtxt.setVisible(true);
    }

    @FXML
    void hidePassword(KeyEvent event) {
        pwd=pwdtxt.getText();
        pwdtxtshow.setText(pwd);
    }

    @FXML
    void open_Eye(MouseEvent event) {
        pwdtxtshow.setVisible(false);
        openEyeIcon.setVisible(false);
        closeEyeIcon.setVisible(true);
        pwdtxt.setVisible(true);
    }

    @FXML
    void showPassword(KeyEvent event) {
        pwd=pwdtxtshow.getText();
        pwdtxt.setText(pwd);
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
