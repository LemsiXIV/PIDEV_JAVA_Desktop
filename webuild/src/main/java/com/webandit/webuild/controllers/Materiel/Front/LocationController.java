package com.webandit.webuild.controllers.Materiel.Front;

import com.google.zxing.common.BitMatrix;
import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.services.ServiceMateriel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;


public class LocationController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    private String lastSearchQuery = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastSearchQuery = ""; // Initialize lastSearchQuery
        displayMateriels("");
        searchButton.setOnAction(this::handleSearch);
    }


    private void handleSearch(ActionEvent event) {
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.equals(lastSearchQuery)) { // Only update if the search query has changed
            displayMateriels(searchQuery);
            lastSearchQuery = searchQuery;
        }
    }

    private void displayMateriels(String searchQuery) {
        grid.getChildren().clear(); // Clear previous materiels from the grid
        ServiceMateriel serviceMateriel = new ServiceMateriel();
        List<Materiel> materiels;
        try {
            materiels = serviceMateriel.searchMateriels(searchQuery); // Use a method in your ServiceMateriel class to search materiels
            int column = 0;
            int row = 0;
            for (Materiel materiel : materiels) {
                if (column == 3) { // Maximum 3 materiels per row
                    column = 0;
                    row++;
                }
                grid.add(createMaterielPane(materiel), column++, row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private VBox createMaterielPane(Materiel materiel) {
        // Materiel Container
        VBox materielContainer = new VBox();
        materielContainer.getStyleClass().add("materiel-container");
        materielContainer.setAlignment(Pos.TOP_CENTER);
        materielContainer.setPadding(new Insets(10));
        materielContainer.setSpacing(10);
        materielContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        materielContainer.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(3.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.LIGHTGRAY);
        materielContainer.setEffect(dropShadow);

        // Image Container
        StackPane imageContainer = new StackPane(); // StackPane to hold the image and the QR code
        imageContainer.setAlignment(Pos.CENTER); // Center the contents
        imageContainer.setMaxSize(150, 150); // Set maximum size for the image container

        // Image
        ImageView imageView = new ImageView(new Image(new File(materiel.getImage()).toURI().toString()));
        imageView.setFitWidth(150); // Set width of the image
        imageView.setFitHeight(150); // Set height of the image

        // Generate QR code for the materiel
        Image qrCodeImage = generateQRCodeImage(materiel.toString(), 50, 50); // Adjust the size of the QR code
        ImageView qrCodeImageView = new ImageView(qrCodeImage);

        // Place the QR code in the bottom right corner of the materiel image
        StackPane.setAlignment(qrCodeImageView, Pos.BOTTOM_RIGHT);

        // Add the image and QR code to the image container
        imageContainer.getChildren().addAll(imageView, qrCodeImageView);

        // Labels Container
        VBox labelsContainer = new VBox();
        labelsContainer.setAlignment(Pos.CENTER_LEFT);
        labelsContainer.setSpacing(5);

        // Libelle
        Label libelleLabel = new Label("Name: " + materiel.getLibelle());
        libelleLabel.getStyleClass().add("materiel-label");

        // Description
        Label descriptionLabel = new Label("Description: " + materiel.getDescription());
        descriptionLabel.getStyleClass().add("materiel-label");
        descriptionLabel.setWrapText(true); // Enable text wrapping
        descriptionLabel.setMaxWidth(200); // Set maximum width for description label

        // Prix
        Label prixLabel = new Label("Price: " + String.valueOf(materiel.getPrix()) + "DT");
        prixLabel.getStyleClass().add("materiel-label");

        // Add labels to the labels container
        labelsContainer.getChildren().addAll(libelleLabel, descriptionLabel, prixLabel);

        // Louer button
        Button louerButton = new Button("Louer");
        louerButton.setMaxWidth(Double.MAX_VALUE);
        louerButton.setOnAction(event -> handleLouer(materiel)); // Set action handler

        // Add elements to the materiel container
        materielContainer.getChildren().addAll(imageContainer, labelsContainer, louerButton);

        // Set VBox alignment to bottom-center
        VBox.setVgrow(labelsContainer, Priority.ALWAYS);
        VBox.setVgrow(louerButton, Priority.ALWAYS);
        VBox.setMargin(louerButton, new Insets(10, 0, 0, 0)); // Add margin to the louer button

        return materielContainer;
    }


    private void handleLouer(Materiel materiel) {
        try {
            // Load the MaterielDetailView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaterielDetailView.fxml"));
            Parent root = loader.load(); // No need to pass InputStream

            // Pass the selected materiel to the MaterielDetailController
            MaterielDetailController controller = loader.getController();
            controller.initData(materiel);

            // Initialize the view with materiel data
            controller.initialize();

            // Create a new stage and set the MaterielDetailView as its content
            Stage stage = new Stage();
            stage.setTitle("Materiel Detail");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Image generateQRCodeImage(String libelle, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = qrCodeWriter.encode(libelle , BarcodeFormat.QR_CODE, width, height, hints);

            // Create a BufferedImage
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Black or White pixel
                }
            }

            // Convert BufferedImage to JavaFX Image
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
            return new Image(new ByteArrayInputStream(os.toByteArray()));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
