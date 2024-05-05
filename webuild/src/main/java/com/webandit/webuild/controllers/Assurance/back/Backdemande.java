package com.webandit.webuild.controllers.Assurance.back;


import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.services.ServiceDemande;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.awt.Desktop;
public class Backdemande {
    @FXML
    private Button pdf;
    @FXML
    private Button valid;
    @FXML
    private TableColumn<Demande, String> colAssurance;

    @FXML
    private TableColumn<Demande, Integer> colMontant;

    @FXML
    private TableColumn<Demande, String> colDebut;

    @FXML
    private TableColumn<Demande, String> colFin;
    @FXML
    private ChoiceBox<String> assurancetxt;

    @FXML
    private Button adddembtn;


    @FXML
    private TableColumn<Demande, Integer> colStatut;

    @FXML
    private TableView<Demande> tabdem;
    @FXML
    private TextField commentairetxt;

    @FXML
    private DatePicker ddebuttxt;

    @FXML
    private Button deldembtn;

    @FXML
    private DatePicker dfintxt;

    @FXML
    private Button moddembtn;

    @FXML
    private TextField montanttxt;





    // Method to initialize the controller
    @FXML
    public void initialize() {
       montanttxt.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Vérifier si le nouveau texte contient uniquement des chiffres
                return change; // Autoriser le changement
            }
            return null; // Bloquer le changement
        }));
        try { ServiceAssurance sa =new ServiceAssurance();
            List<Assurance> assurances = sa.selectAll();
            populateAssuranceChoiceBox(assurances);
            showDemandes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ddebuttxt.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new date is before the current date
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // Show an error message
                showAlert("Error", "La date sélectionnée ne peux pas etre dans le passé.");
                // Reset the DatePicker to the current date
                ddebuttxt.setValue(LocalDate.now());
            }
        });
        dfintxt.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new date is before the current date
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // Show an error message
                showAlert("Erreur", "La date sélectionnée ne peux pas etre dans le passé.");
                // Reset the DatePicker to the current date
                dfintxt.setValue(LocalDate.now());
            }
        });


    }

    // Method to fetch and display all demandes in the TableView
    private void showDemandes() throws SQLException {
        // Fetch all demandes from the database
        ServiceDemande  ServiceDemande= new ServiceDemande();
        List<Demande> demandes = ServiceDemande.selectAll();

        // Convert the list to an ObservableList
        ObservableList<Demande> demandeList = FXCollections.observableArrayList(demandes);

        // Bind the ObservableList to the TableView
        tabdem.setItems(demandeList);

        colAssurance.setCellValueFactory(cellData -> {
            // Access the Assurance object associated with the Demande
            Assurance assurance = cellData.getValue().getA();
            if (assurance != null) {
                // Return the name of the assurance
                return new SimpleStringProperty(assurance.getNom());
            } else {
                // If assurance is null, return an empty string
                return new SimpleStringProperty("");
            }
        });
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

        colStatut.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    @FXML
    void addDemande(ActionEvent event) {

            // Get values from the input fields
        String montantStr = montanttxt.getText();

        Date debut = Date.valueOf(ddebuttxt.getValue());
        Date fin = Date.valueOf(dfintxt.getValue());
        String commentaire = commentairetxt.getText();


        // Convert montant from String to int
        int montant = Integer.parseInt(montantStr);

        // Parse debut and fin strings into Date objects

        String selectedAssuranceName = assurancetxt.getValue();

        // Check if an assurance name is selected
        if (selectedAssuranceName == null) {
            // Handle the case where no assurance is selected (show error message, etc.)
            return;
        }

        // Retrieve the assurance instance based on its name
        Assurance selectedAssurance = null;
        try {ServiceAssurance sa =new ServiceAssurance();
        List<Assurance> assurances = sa.selectAll();
            for (Assurance assurance : assurances) {
                if (assurance.getNom().equals(selectedAssuranceName)) {
                    selectedAssurance = assurance;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Check if the selected assurance was found
        if (selectedAssurance == null) {
            // Handle the case where the selected assurance was not found (show error message, etc.)
            return;
        }

            // Set default values for user and status
            int defaultUser = 4;
                Integer defaultStatus = 0; // Using Integer instead of int to allow null values





        Demande demande = new Demande();
       demande.setA(selectedAssurance);
        demande.setMontant(montant);
        demande.setUser(defaultUser);
        demande.setDate_debut(debut);
        demande.setDate_fin(fin);
        demande.setCommentaire(commentaire);
       demande.setStatus(defaultStatus);


            try {
                ServiceDemande serviceDemande = new ServiceDemande();
                // Use the service to add the demande
                serviceDemande.insertOne(demande);
                System.out.println("Demande ajoutée avec succès !");



                clearFields();
                showDemandes();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the error appropriately (e.g., show an error message to the user)
            }
        }


    private void clearFields() {
        montanttxt.clear();
        ddebuttxt.setValue(null);
        dfintxt.setValue(null);
        commentairetxt.clear();
    }
    private void populateAssuranceChoiceBox(List<Assurance> assurances) {
        ObservableList<String> assuranceNames = FXCollections.observableArrayList();
        for (Assurance assurance : assurances) {
            assuranceNames.add(assurance.getNom());
        }
        assurancetxt.setItems(assuranceNames);

    }
    @FXML
    void deldem(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            return;
        }

        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            // Use the service to delete the demande
            serviceDemande.deleteOne(selectedDemande);
            System.out.println("Demande supprimée avec succès !");
            // Refresh the TableView after deletion
            showDemandes();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }

    @FXML
    void getdatadem(MouseEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande != null) {
            // Set the data of the selected demande to the input fields
            montanttxt.setText(String.valueOf(selectedDemande.getMontant()));
            ddebuttxt.setValue(selectedDemande.getDate_debut().toLocalDate());
            dfintxt.setValue(selectedDemande.getDate_fin().toLocalDate());
            commentairetxt.setText(selectedDemande.getCommentaire());

            // Set the selected assurance name in the ChoiceBox
            String selectedAssuranceName = selectedDemande.getA().getNom();
            assurancetxt.setValue(selectedAssuranceName);

        }
    }

    @FXML
    void moddem(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            return;
        }

        // Get values from the input fields
        String montantStr = montanttxt.getText();
        Date debut = Date.valueOf(ddebuttxt.getValue());
        Date fin = Date.valueOf(dfintxt.getValue());
        String commentaire = commentairetxt.getText();
        String selectedAssuranceName = assurancetxt.getValue(); // Get the selected assurance from the ChoiceBox

        // Convert montant from String to int
        int montant = Integer.parseInt(montantStr);

        // Parse debut and fin strings into Date objects


        if (selectedAssuranceName == null) {
            return;
        }
        Assurance selectedAssurance = null;
        try {
            ServiceAssurance sa = new ServiceAssurance();
            List<Assurance> assurances = sa.selectAll();
            for (Assurance assurance : assurances) {
                if (assurance.getNom().equals(selectedAssuranceName)) {
                    selectedAssurance = assurance;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (selectedAssurance == null) {
            return;
        }
        selectedDemande.setA(selectedAssurance);
        selectedDemande.setMontant(montant);
        selectedDemande.setDate_debut(debut);
        selectedDemande.setDate_fin(fin);
        selectedDemande.setCommentaire(commentaire);

        try {
            ServiceDemande serviceDemande = new ServiceDemande();
            // Use the service to update the demande in the database
            serviceDemande.updateOne(selectedDemande);
            System.out.println("Demande modifiée avec succès !");
            // Refresh the TableView after modification
            showDemandes();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void RejectDemande(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            showAlert("Error", "No demande selected.");
            return;
        }

        // Set the status of the demande to 1 (approved)
        selectedDemande.setStatus(2);

        try {
            // Update the status in the database
            ServiceDemande serviceDemande = new ServiceDemande();
            serviceDemande.updateStatus(selectedDemande.getId_d(), 2);

            // Send an SMS notification to the client
            String clientPhoneNumber = "+21698134491"; // Get the client's phone number from the demande or any other source
            String message = "On est desolé, Votre demande pour l'assurance "+ selectedDemande.getA().getNom()+" a été rejetée."; // Message to send
            TwilioSMSFXML.sendSMS(clientPhoneNumber, message);

            // Show a success message
            showAlert("success message ", "Demande rejeter et notification envoyée.");

            // Refresh the TableView
            showDemandes();

            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }
    @FXML
    void checkAndValidateDemande(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

        // Check if a demande is selected
        if (selectedDemande == null) {
            // Handle the case where no demande is selected (show error message, etc.)
            showAlert("Error", "No demande selected.");
            return;
        }

        try {
            // Validate the demande
            ServiceDemande serviceDemande = new ServiceDemande();
            boolean validated = serviceDemande.validDemande(selectedDemande);
            // Show appropriate message based on validation result
            if (validated) {
                showAlert("Success", "La demande a été revue et approuvée avec succès.");
            } else {
                showAlert("Success", "La demande est encore en attente.");
            }
            // Refresh the TableView after modification
            showDemandes();
            // Clear the input fields
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }
    @FXML
    void generateContract(ActionEvent event) {
        // Get the selected demande from the TableView
        Demande selectedDemande = tabdem.getSelectionModel().getSelectedItem();

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

    // Existing controller code...


}










