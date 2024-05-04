package com.webandit.webuild.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ResetPwd {

    @FXML
    private TextField code;

    @FXML
    private Button envoyer_email;

    @FXML
    private TextField email;

    private String generatedCode;
    @FXML
    private Button codeVerifier;

    private String generateVerificationCode() {
        // Generate a random 6-digit code
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    @FXML
    void validerEmail(ActionEvent event) {

        String emailAddress = email.getText();
        if (!isValidEmail(emailAddress)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        generatedCode = generateVerificationCode();
        try {
            sendEmail(emailAddress, "Verification Code", "Your verification code is: " + generatedCode);
            showAlert(Alert.AlertType.INFORMATION, "Email Sent", "A verification code has been sent to your email address.");
        } catch (MessagingException e) {
            showAlert(Alert.AlertType.ERROR, "Error Sending Email", "Failed to send the verification code. Please try again later.");
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        // You can implement email validation using regular expressions or libraries like Apache Commons Validator
        // For simplicity, we'll perform basic validation here
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        // SMTP server configuration
        String host = "smtp.gmail.com"; // Replace with the actual SMTP server address
        String username = ""; // Replace with your email address
        String password = ""; // Replace with your email password
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

    @FXML
    void verifierCode(ActionEvent event) {
        String enteredCode = code.getText();
        if (enteredCode.equals(generatedCode)) {
            showAlert(Alert.AlertType.INFORMATION, "Code Verified", "Verification code is correct. You can proceed.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Change-pwd.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) codeVerifier.getScene().getWindow();
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
}
