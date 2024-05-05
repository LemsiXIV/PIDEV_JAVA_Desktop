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
            document.add(new Paragraph("Contrat d'Assurance\n" + selectedDemande.getId_d()+
                    "\n" +
                    "Entre l'assureur WEBUILD, une société d'assurance enregistrée selon les lois en vigueur en Tunisie, avec son siège social situé à Ariana, " +
                    "\n" +
                    "Et l'Assuré,\n" +
                    "\n" +
                    "Il est convenu ce qui suit :\n" +
                    "\n" +
                    "1. Objet de l'Assurance : "+ selectedDemande.getA().getNom()+"\n" +
                    "L'Assureur s'engage à fournir à l'Assuré une couverture d'assurance conformément aux termes et conditions spécifiés dans le présent contrat, pour les risques et événements assurés tels que définis dans les annexes et documents complémentaires joints à ce contrat.\n" +
                    "\n" +
                    "2. Durée du Contrat : de "+ selectedDemande.getDate_debut()+" à "+ selectedDemande.getDate_fin()+"\n" +
                    "Le présent contrat entre en vigueur à partir de la date de signature par les deux parties et demeure en vigueur pour une période de [durée], sauf résiliation anticipée conformément aux dispositions prévues dans ce contrat.\n" +
                    "\n" +
                    "3. Prime d'Assurance :" + selectedDemande.getA().getPrime()+"\n" +
                    "En contrepartie de la couverture d'assurance fournie par l'Assureur, l'Assuré s'engage à payer à l'Assureur une prime d'assurance périodique de"+  selectedDemande.getA().getPrime()+" selon les modalités et les échéances convenues entre les parties.\n" +
                    "\n" +
                    "4. Obligations de l'Assuré :\n" +
                    "L'Assuré s'engage à fournir à l'Assureur toutes les informations et documents nécessaires à l'évaluation du risque assuré, ainsi qu'à notifier à l'Assureur tout changement significatif dans les circonstances qui pourraient affecter l'assurance couverte par ce contrat.\n" +
                    "\n" +
                    "5. Obligations de l'Assureur :\n" +
                    "L'Assureur s'engage à fournir à l'Assuré une couverture d'assurance conforme aux termes de ce contrat, et à traiter toute réclamation de manière équitable et en temps opportun, conformément aux dispositions légales et réglementaires en vigueur.\n" +
                    "\n" +
                    "6. Réclamations :\n" +
                    "En cas de survenance d'un événement assuré, l'Assuré s'engage à notifier à l'Assureur dans les délais spécifiés dans les conditions générales de ce contrat, et à fournir à l'Assureur toutes les preuves et informations nécessaires à la gestion de la réclamation.\n" +
                    "\n" +
                    "7. Résiliation :\n" +
                    "Ce contrat peut être résilié par l'une ou l'autre des parties conformément aux conditions générales et particulières spécifiées dans les documents contractuels.\n" +
                    "\n" +
                    "8. Loi Applicable et Juridiction compétente :\n" +
                    "Ce contrat est régi par les lois en vigueur en Tunisie. Tout litige découlant de ce contrat sera soumis à la juridiction exclusive des tribunaux compétents de Tunis.\n" +
                    "\n"
            ));
            // Add content to the PDF document
            document.add(new Paragraph("Contract for Demande ID: " + selectedDemande.getId_d()));
            document.add(new Paragraph("Montant: " + selectedDemande.getMontant()));
            document.add(new Paragraph("Date Debut: " + selectedDemande.getDate_debut()));
            document.add(new Paragraph("Date Fin: " + selectedDemande.getDate_fin()));
            // Add more demande-specific details as needed

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
