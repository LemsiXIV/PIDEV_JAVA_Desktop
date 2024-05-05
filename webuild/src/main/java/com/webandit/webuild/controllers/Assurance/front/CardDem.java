package com.webandit.webuild.controllers.Assurance.front;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.services.ServiceDemande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.webandit.webuild.models.Assurance;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardDem {






    @FXML
    private Label Nom;

    @FXML
    private Label montant;

    @FXML
    private Label datedeb;

    @FXML
    private Label dateffin;

    @FXML
    private Label com;


    private Demande demande;
    private final ServiceDemande serviceDemande = new ServiceDemande();

    public void setData(Demande demande) {
        this.demande = demande;
        // Set image

        Nom.setText("Type"+demande.getA().getNom());
        montant.setText("MNT: "+demande.getMontant());

        datedeb.setText("Date d: " + demande.getDate_debut());
       dateffin.setText("Date f: " + demande.getDate_fin());
        setStatusText();
    }
    private void setStatusText() {
        String statusText;
        Color color;

        switch (demande.getStatus()) {
            case 0:
                statusText = "Pending";
                color = Color.ORANGE;
                break;
            case 1:
                statusText = "Validated";
                color = Color.GREEN;
                break;
            case 2:
                statusText = "Rejected";
                color = Color.RED;
                break;
            default:
                statusText = "Unknown";
                color = Color.BLACK;
                break;
        }     com.setText(statusText);
       com.setTextFill(color);


    }
    int id;
    public void setId(int id){this.id=id;}
    @FXML
    void generateContract(ActionEvent event) {

        // Get the selected demande from the TableView
        Demande selectedDemande = demande ;

        // Check if a demande is selected
        if (selectedDemande == null) {
            showAlert("Error", "No demande selected.");
            return;
        }

        // Check if the demande is validated (status = 1)
        if (selectedDemande.getStatus() != 1) {
            showAlert("Error", "Only validated demandes can generate contracts.");
            return;
        }

        try {    String pdfFileName = "contract_" + selectedDemande.getId_d() + ".pdf";
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFileName));
            Document document = new Document(pdfDocument);

            // Add content to the PDF document
            document.add(new Paragraph("Contract for Demande ID: " + selectedDemande.getId_d()));
            document.add(new Paragraph("Montant: " + selectedDemande.getMontant()));
            document.add(new Paragraph("Date Debut: " + selectedDemande.getDate_debut()));
            document.add(new Paragraph("Date Fin: " + selectedDemande.getDate_fin()));
            // Add more demande-specific details as needed

            // Close the document
            document.close();

            // Show a success message
            showAlert("Success", "Contract generated successfully for Demande ID: " + selectedDemande.getId_d());
            // Open the generated PDF file
            File file = new File(pdfFileName);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                showAlert("Error", "Failed to open generated contract.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to generate contract.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
