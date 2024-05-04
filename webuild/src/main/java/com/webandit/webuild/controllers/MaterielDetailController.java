package com.webandit.webuild.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import com.webandit.webuild.models.Location;
import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.services.ServiceLocation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.HostServices;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MaterielDetailController {

    @FXML
    private Label libelleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField userIdTextField;

    private Materiel materiel;

    private HostServices hostServices;

    public void initHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void initialize() {
        System.out.println("MaterielDetailController initialized.");
        if (materiel != null) {
            libelleLabel.setText(materiel.getLibelle());
            descriptionLabel.setText(materiel.getDescription());
            prixLabel.setText("Price: " + materiel.getPrix() + " DT");
        } else {
            System.out.println("Materiel is null.");
        }
    }


    public void initData(Materiel materiel) {
        this.materiel = materiel;
    }

    @FXML
    private void confirmLocation() {
        // Implement logic for confirming the location
        if (dateDebutPicker.getValue() != null && dateFinPicker.getValue() != null) {
            // Check if the selected start date is before today's date
            if (dateDebutPicker.getValue().isBefore(LocalDate.now())) {
                showAlert("date debut non valide", "Veuillez choisir une date valide.");
                return;
            }

            // Check if the selected end date is before today's date
            if (dateFinPicker.getValue().isBefore(LocalDate.now())) {
                showAlert("date fin non valide", "Veuillez choisir une date valide.");
                return;
            }

            String userId = userIdTextField.getText().trim();
            if (userId.isEmpty()) {
                showAlert("User ID Empty", "Please enter a user ID.");
                return;
            }
            // Proceed with the rental confirmation
            System.out.println("Location confirmed for: " + materiel.getLibelle());
            System.out.println("Date début location: " + dateDebutPicker.getValue());
            System.out.println("Date fin location: " + dateFinPicker.getValue());
            saveLocation(userId);
            // Add your logic here to proceed with the rental confirmation
        } else {
            showAlert("Missing Dates", "Please select both start and end dates.");
        }initiateStripePayment();
    }
    private void initiateStripePayment() {
        // Set your Stripe secret key
        Stripe.apiKey = "sk_test_51OoUFtBZpJDBcUiHu12Mf2JS1XnbT15zVgV7SUGxKXKVEL1samRuq93jaQ0Pf4yAq6R4JSM23o4OyTEO35oJNC5n00Z3H4gECB"; // Replace with your Stripe secret key

        // Create a new Payment Session with Stripe Checkout
        try {
            Session session = Session.create(createCheckoutSession());

            // Redirect the user to the Stripe Checkout page
            String checkoutUrl = session.getUrl();
            // Open checkoutUrl in a browser or WebView
            System.out.println("Redirect user to: " + checkoutUrl);
        } catch (StripeException e) {
            e.printStackTrace();
            showAlert("Stripe Error", "Failed to create checkout session. Please try again later.");
        }

    }

    private SessionCreateParams createCheckoutSession() {
        // Set up the parameters for creating a Stripe Checkout session
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://your-website.com/success") // Replace with your success URL
                .setCancelUrl("https://your-website.com/cancel") // Replace with your cancel URL
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd") // Replace with your currency
                                                .setUnitAmount(1000L) // Replace with your amount in cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Your Product Name")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }
    private void saveLocation(String userId) {
        try {
            // Create a Location object with the selected dates and materiel ID
            Location location = new Location();
            location.setId_user(Integer.parseInt(userId));
            location.setDate_d(java.sql.Date.valueOf(dateDebutPicker.getValue()));
            location.setDate_f(java.sql.Date.valueOf(dateFinPicker.getValue()));
            location.setM(materiel);

            // Save the location details into the database using the ServiceLocation class
            ServiceLocation serviceLocation = new ServiceLocation();
            serviceLocation.insertOne(location);

            // Show a success message to the user
            showSuccessAlert("Success", "Location details saved successfully.");
        } catch (SQLException e) {
            // Handle any SQL exceptions
            showAlert("Error", "Failed to save location details. Please try again later.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.initStyle(StageStyle.UNDECORATED);

        try {
            // Load the green checkmark icon image
            InputStream inputStream = getClass().getResourceAsStream("green_check.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(48); // Adjust size as needed
                imageView.setFitHeight(48);
                alert.setGraphic(imageView);
            } else {
                System.out.println("Failed to load green_check.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set green color for the alert dialog background
        alert.getDialogPane().setStyle("-fx-background-color: #C8E6C9"); // Light green color

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
