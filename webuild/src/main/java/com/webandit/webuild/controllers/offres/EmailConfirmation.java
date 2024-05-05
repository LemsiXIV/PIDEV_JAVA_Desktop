package com.webandit.webuild.controllers.offres;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailConfirmation {

    @FXML
    private TextField adres;

    @FXML
    private TextArea emailcontent;

    @FXML
    private TextField objet;

    @FXML
    private Button sendBtn;

    @FXML
    void SendConfirmation(ActionEvent event) {

        String recipientEmail = adres.getText();
        String subject = objet.getText();
        String content = emailcontent.getText();

        // Email sender's details
        String senderEmail = "webuild2026@gmail.com";
        String senderPassword = "sgwc ggbh plhj auth";

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port
        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MIME message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);

            // Confirmation message
            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }

}
