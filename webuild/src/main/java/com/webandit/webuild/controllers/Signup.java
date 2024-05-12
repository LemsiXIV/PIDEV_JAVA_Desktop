package com.webandit.webuild.controllers;

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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

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
    private PasswordField pwdtxt;



    @FXML
    private PasswordField confirmPass;

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

    @FXML
    private ImageView closeEyeIcon;

    @FXML
    private ImageView openEyeIcon;

    @FXML
    private TextField pwdtxtshow;

    @FXML
    private ImageView closeEyeIcon1;

    @FXML
    private ImageView openEyeIcon1;

    @FXML
    private TextField showconfpwd;

    @FXML
    private Label confirmerr;
    @FXML
    private TextField cintxt;
    @FXML
    private TextField biotxt;
    @FXML
    private DatePicker datetxt;
    @FXML
    private TextField fonctiontxt;

    private String confpwd;

    private String pwd;



    private String generatedCode;
    @FXML
    private ImageView backButton;

    public void initialize(){
        pwdtxtshow.setVisible(false);
        openEyeIcon.setVisible(false);
        showconfpwd.setVisible(false);
        openEyeIcon1.setVisible(false);
        LocalDate localDate = LocalDate.now(); // Set default date to today's date
        datetxt.setValue(localDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }



    @FXML
    void addUtilisateur(ActionEvent event) {
        serviceUtilisateur sp = new serviceUtilisateur();
       /* if (!validatorNom() || !validatorPrenom() || !validatorAdresse() || !validatorEmail() || !validatorPassword() || !validatorTelephone() || !validatorConfirmPassword()) {
            return;
        }*/
        try {
            String email = emailtxt.getText();
            if (sp.selectByEmail(email)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Exists");
                alert.setContentText("A user with the same email already exists.");
                alert.show();
            } else {
                generatedCode = generateVerificationCode();
                LocalDate localDate = datetxt.getValue();
                java.sql.Date date = java.sql.Date.valueOf(localDate);
                String pass = generateVerificationHash(pwdtxt.getText());
                User u = new User(emailtxt.getText(), pass, nomtxt.getText(), prenomtxt.getText(), telephonetxt.getText(), cintxt.getText(), fonctiontxt.getText(), adressetxt.getText(), date, biotxt.getText(), Arrays.asList("\"ROLE_USER\""), 0, 0);
                u.setRoles(Arrays.asList("\"ROLE_USER\""));
                sp.insertOne(u);
                SessionManagement sessionManagement = new SessionManagement(u.getId(), u.getEmail(),u.getPassword(), u.getNom(), u.getPrenom(), u.getTelephone(),u.getCin(),u.getFonction(), u.getAddress(),u.getDate(),u.getBio(), u.getRoles(), u.isIs_Banned(),u.isIs_verified());
                // Set the current session
                SessionManagement.setInstance(sessionManagement);
                try {
                    sendEmail(email, "Verification Code", "Your verification code is: " + generatedCode);
                    showAlert(Alert.AlertType.INFORMATION, "Email Sent", "A verification code has been sent to your email address.");
                } catch (MessagingException e) {
                    showAlert(Alert.AlertType.ERROR, "Error Sending Email", "Failed to send the verification code. Please try again later.");
                    e.printStackTrace();
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Verifier-account.fxml"));
            Parent root = loader.load();
            VerifierAccount verifierAccountController = loader.getController(); // Get the controller instance
            verifierAccountController.setCodeG(generatedCode);
            //verifierAccountController.getEmailCurrent(email);// Set the generated code
            Scene scene = new Scene(root);
            Stage stage = (Stage) savebtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException | IOException e){
            System.out.println("err" + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }

    @FXML
    void close_Eye(MouseEvent event) {
        pwdtxtshow.setVisible(true);
        openEyeIcon.setVisible(true);
        closeEyeIcon.setVisible(false);
        pwdtxt.setVisible(false);
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
    void hidePassword1(KeyEvent event) {
        confpwd=confirmPass.getText();
        showconfpwd.setText(confpwd);
    }

    @FXML
    void open_Eye1(MouseEvent event) {
        showconfpwd.setVisible(false);
        openEyeIcon1.setVisible(false);
        closeEyeIcon1.setVisible(true);
        confirmPass.setVisible(true);
    }

    @FXML
    void showPassword1(KeyEvent event) {
        confpwd=showconfpwd.getText();
        confirmPass.setText(confpwd);
    }

    @FXML
    void close_Eye1(MouseEvent event) {
        showconfpwd.setVisible(true);
        openEyeIcon1.setVisible(true);
        closeEyeIcon1.setVisible(false);
        confirmPass.setVisible(false);
    }

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
            return false;
        } else{
            return true;
        }
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

    private boolean validatorConfirmPassword(){
        String pwd=pwdtxt.getText();
        String confirmPwd=confirmPass.getText();
        if(!pwd.equals(confirmPwd)){
            confirmerr.setStyle("-fx-text-fill: red;");
            confirmerr.setText("Passwords do not match");
            confirmerr.requestFocus();
            return false;
        }
        return true;
    }

    private String generateVerificationCode() {
        // Generate a random 6-digit code
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        // SMTP server configuration
        String host = "smtp.gmail.com"; // Replace with the actual SMTP server address
        String username = "hadil.miladi@esprit.tn"; // Replace with your email address
        String password = "h1a2d3i4l5"; // Replace with your email password
        int port = 587; // Change the port if required

        // Set properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create a Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create a MimeMessage object
        Message message = new MimeMessage(session);

        // Set From: header field
        message.setFrom(new InternetAddress(username));

        // Set To: header field
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        // Set Subject: header field
        message.setSubject(subject);

        // Set the actual message
        message.setText(body);

        // Send message
        Transport.send(message);

        System.out.println("Email sent successfully!");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
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
    private String generateVerificationHash(String password) {
        try {
            // Create a MessageDigest instance for the SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Hash the password
            byte[] hashBytes = digest.digest(password.getBytes());
            // Encode the hash into a Base64 string
            String hashString = Base64.getEncoder().encodeToString(hashBytes);
            // Concatenate the prefix and the hashed password
            return "$2y$13$" + hashString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
