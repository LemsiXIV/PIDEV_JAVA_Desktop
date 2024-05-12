package com.webandit.webuild.controllers.front;

import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.User;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UserProfilController {

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
    @FXML
    private ImageView backButton;
    @FXML
    private Button logoutButton;
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
        User u = new User(nomtxt.getText(), prenomtxt.getText(), tlptxt.getText(), adressetxt.getText(), emailtxt.getText(),cintxt.getText(),fonctiontxt.getText(),date,biotxt.getText());
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
        adressetxt.setText(SessionManagement.getInstance().getAddress());
        tlptxt.setText(SessionManagement.getInstance().getTelephone());
        cintxt.setText(SessionManagement.getInstance().getCin());
        biotxt.setText(SessionManagement.getInstance().getBio());
        SessionManagement session = SessionManagement.getInstance();
        Date sessionDate = session.getDate();
        LocalDate localDate = sessionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datetxt.setValue(localDate);
        fonctiontxt.setText(SessionManagement.getInstance().getFonction());
    }


    @FXML
    void deleteUser(ActionEvent event) {
        User u = new User(nomtxt.getText(), prenomtxt.getText(), tlptxt.getText(), adressetxt.getText(), emailtxt.getText(),cintxt.getText(),fonctiontxt.getText(),date,biotxt.getText());

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

    @FXML
    void logout(ActionEvent event) {
        SessionManagement.getInstance().cleanUserSession();
        System.out.println("User ID: " + SessionManagement.getInstance().getId());
        System.out.println("User Nom: " + SessionManagement.getInstance().getNom());
        System.out.println("User Prenom: " + SessionManagement.getInstance().getPrenom());
        System.out.println("User Email: " + SessionManagement.getInstance().getEmail());
        System.out.println("User Adresse: " + SessionManagement.getInstance().getAddress());
        System.out.println("User Telephone: " + SessionManagement.getInstance().getTelephone());
        System.out.println("User Role: " + SessionManagement.getInstance().getRoles());
        System.out.println("User status: " + SessionManagement.getInstance().isIs_verified());
        System.out.println("User status: " + SessionManagement.getInstance().isIs_Banned());

        // Navigate to the login page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/front/hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}