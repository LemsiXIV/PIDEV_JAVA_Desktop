package com.webandit.webuild.controllers;
import com.webandit.webuild.models.User;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import at.favre.lib.crypto.bcrypt.BCrypt;
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
            String email = emailtxt.getText();
            String password = serviceutilisater.getPassByEmail(email);
            BCrypt.Result result = BCrypt.verifyer().verify(pwdtxt.getText().getBytes(), password.getBytes());

            if (result.verified) {
                User utilisateur = serviceutilisater.Login(email);

                if (utilisateur != null && utilisateur.isIs_verified() == 1 && utilisateur.isIs_Banned() == 0) {
                    SessionManagement session = SessionManagement.getInstance(
                            utilisateur.getId(), utilisateur.getEmail(), utilisateur.getPassword(),
                            utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone(),
                            utilisateur.getCin(), utilisateur.getFonction(), utilisateur.getAddress(),
                            utilisateur.getDate(), utilisateur.getBio(), utilisateur.getRoles(),
                            utilisateur.isIs_Banned(), utilisateur.isIs_verified()
                    );

                    System.out.println("User ID: " + session.getId());
                    System.out.println("User Nom: " + session.getNom());
                    System.out.println("User Prenom: " + session.getPrenom());
                    System.out.println("User Email: " + session.getEmail());
                    System.out.println("User Adresse: " + session.getAddress());
                    System.out.println("User Telephone: " + session.getTelephone());
                    System.out.println("User CIN: " + session.getCin());
                    System.out.println("User Bio: " + session.getBio());
                    System.out.println("User Password: " + session.getPassword());
                    System.out.println("User Fonction: " + session.getFonction());
                    System.out.println("User Date: " + session.getDate());
                    System.out.println("User Status: " + session.isIs_verified());
                    System.out.println("User Role: " + session.getRoles());
                    System.out.println("Account status: " + session.isIs_Banned());
                    System.out.println("instance " + session);

                    String fxmlPath = session.getRoles().contains("[\"ROLE_ADMIN\"]") ? "/fxml/backadmine.fxml" : "/fxml/hello-view.fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) LoginButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if (utilisateur != null) {
                        if (utilisateur.isIs_verified() == 0) {
                            alert.setTitle("Pas d'accès");
                            alert.setContentText("Votre compte n'est pas vérifié.");
                        } else if (utilisateur.isIs_Banned() == 1) {
                            alert.setTitle("Pas d'autorisation");
                            alert.setContentText("Votre compte n'est pas autorisé à se connecter.");
                        }
                    } else {
                        alert.setTitle("Erreur de connexion");
                        alert.setContentText("Adresse e-mail ou mot de passe incorrect.");
                    }
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setContentText("Adresse e-mail ou mot de passe incorrect.");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de la connexion.");
            alert.show();
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



