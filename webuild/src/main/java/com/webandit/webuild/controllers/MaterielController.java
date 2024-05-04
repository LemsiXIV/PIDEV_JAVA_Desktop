package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.utils.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterielController implements Initializable {
    private Connection cnx;
    private File selectedImageFile;
    @FXML
    private TableColumn<Materiel, String> descriptionColumn;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TableColumn<Materiel, Integer> idColumn;

    @FXML
    private TableColumn<Materiel, String> imageColumn;

    @FXML
    private TableColumn<Materiel, String> libelleColumn;

    @FXML
    private TextField libelleField;

    @FXML
    private TableColumn<Materiel, Integer> prixColumn;

    @FXML
    private TextField prixField;

    @FXML
    private Button savebtn;

    @FXML
    private Button savebtn2;

    @FXML
    private Button savebtn3;

    @FXML
    private TableView<Materiel> tableMateriel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prixField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Vérifier si le nouveau texte contient uniquement des chiffres
                return change; // Autoriser le changement
            }
            return null; // Bloquer le changement
        }));
        showMateriel();
    }

    @FXML
    void saveMateriel(ActionEvent event) {
        String libelle = libelleField.getText().trim();
        String description = descriptionField.getText().trim();
        int prix;

        // Validate input fields
        if (libelle.isEmpty() || description.isEmpty() || prixField.getText().isEmpty()) {
            showErrorDialog("Empty fields", "Please fill all the fields.");
            return;
        }

        try {
            prix = Integer.parseInt(prixField.getText());
            if (prix < 0) {
                showErrorDialog("Invalid Price", "Price must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid Input", "Please enter a valid price.");
            return;
        }

        // Set the image path
        String imagePath = ""; // Initialize with an empty string
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }

        // Create the new Materiel object with the image path
        Materiel newMateriel = new Materiel(libelle, description, prix, imagePath);

        // Save materiel
        try {
            com.webandit.webuild.services.ServiceMateriel serviceMateriel = new com.webandit.webuild.services.ServiceMateriel();
            serviceMateriel.insertOne(newMateriel);
            tableMateriel.getItems().clear(); // Clear existing items
            tableMateriel.getItems().addAll(getMateriel());
            clearFields();
            System.out.println("Materiel added successfully!");
        } catch (SQLException e) {
            showErrorDialog("Error", "Failed to save materiel: " + e.getMessage());
        }
    }


    @FXML
    void handleTableViewSelection(MouseEvent event) {
        if (event.getClickCount() == 1) { // Check for single click
            // Get the selected item from the table view
            Materiel selectedMateriel = tableMateriel.getSelectionModel().getSelectedItem();
            if (selectedMateriel != null) {
                // Fill the text fields with the attributes of the selected materiel
                libelleField.setText(selectedMateriel.getLibelle());
                descriptionField.setText(selectedMateriel.getDescription());
                prixField.setText(String.valueOf(selectedMateriel.getPrix()));

                // Set a lambda function to update the materiel when the Save button is clicked
                savebtn2.setOnAction(e -> updateMateriel(selectedMateriel));
            }
        }
    }

    @FXML
    void importImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedImageFile = fileChooser.showOpenDialog(null); // Update selectedImageFile
        if (selectedImageFile != null) {
            // Set the image path in the Materiel object
            String imagePath = selectedImageFile.getAbsolutePath();
            // Update the selected materiel's image path
            Materiel selectedMateriel = tableMateriel.getSelectionModel().getSelectedItem();
            if (selectedMateriel != null) {
                selectedMateriel.setImage(imagePath);
            }
        }
    }


    @FXML
    void updateMateriel(Materiel materiel) {
        String libelle = libelleField.getText().trim();
        String description = descriptionField.getText().trim();
        int prix;

        // Validate input fields
        if (libelle.isEmpty() || description.isEmpty() || prixField.getText().isEmpty()) {
            showErrorDialog("Empty fields", "Please fill all the fields.");
            return;
        }

        try {
            prix = Integer.parseInt(prixField.getText());
            if (prix < 0) {
                showErrorDialog("Invalid Price", "Price must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid Input", "Please enter a valid price.");
            return;
        }

        // Update the attributes of the selected materiel
        materiel.setLibelle(libelle);
        materiel.setDescription(description);
        materiel.setPrix(prix);

        // Update materiel in the database
        try {
            com.webandit.webuild.services.ServiceMateriel serviceMateriel = new com.webandit.webuild.services.ServiceMateriel();
            serviceMateriel.updateOne(materiel); // Assuming updateOne method is implemented to update existing materiel
            tableMateriel.getItems().clear(); // Clear existing items
            tableMateriel.getItems().addAll(getMateriel());
            clearFields();
            System.out.println("Materiel updated successfully!");
        } catch (SQLException e) {
            showErrorDialog("Error", "Failed to update materiel: " + e.getMessage());
        }
    }

    @FXML
    void deleteMateriel(ActionEvent event) {
        // Get the selected item from the TableView
        Materiel selectedMateriel = tableMateriel.getSelectionModel().getSelectedItem();
        if (selectedMateriel == null) {
            showErrorDialog("No Selection", "Veuillez selectionner une pack materiel a supprimer.");
            return;
        }

        // Confirm deletion with user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer suppression");
        alert.setHeaderText("Suprrimer pack materiel");
        alert.setContentText("Etes-vous sure de supprimer ce pack?");
        Optional<ButtonType> result = alert.showAndWait();

        // Delete the materiel if user confirms
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Delete materiel from the database
                com.webandit.webuild.services.ServiceMateriel serviceMateriel = new com.webandit.webuild.services.ServiceMateriel();
                serviceMateriel.deleteOne(selectedMateriel); // Pass the selectedMateriel object

                // Remove the selected materiel from the TableView
                tableMateriel.getItems().remove(selectedMateriel);

                System.out.println("Materiel deleted successfully!");
            } catch (SQLException e) {
                showErrorDialog("Error", "Failed to delete materiel: " + e.getMessage());
            }
        }
    }

    public ObservableList<Materiel> getMateriel() {
        ObservableList<Materiel> materiels = FXCollections.observableArrayList();
        String query = "SELECT * FROM pack_materiel";
        cnx = DBConnection.getInstance().getCnx();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Materiel a = new Materiel();
                a.setId(rs.getInt("id"));
                a.setLibelle(rs.getString("libelle"));
                a.setDescription(rs.getString("description"));
                a.setPrix(rs.getInt("prix"));
                a.setImage(rs.getString("image"));
                materiels.add(a); // Ajouter l'assurance à la liste

                System.out.println("Materiel récupérée : " + a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materiels;
    }

    private void clearFields() {
        libelleField.clear();
        descriptionField.clear();
        prixField.clear();
    }

    public void showMateriel() {
        ObservableList<Materiel> list = getMateriel();
        tableMateriel.setItems(list);

        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        imageColumn.setCellValueFactory(cellData -> {
            StringProperty imagePathProperty = new SimpleStringProperty(cellData.getValue().getImage());
            return imagePathProperty;
        });

        // Custom cell factory for the image column
        imageColumn.setCellFactory(column -> {
            return new TableCell<Materiel, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (imagePath == null || empty) {
                        setGraphic(null);
                    } else {
                        try {
                            File file = new File(imagePath);
                            Image image = new Image(file.toURI().toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(50); // Adjust width as needed
                            imageView.setPreserveRatio(true);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        });
    }

    private void showErrorDialog(String title, String message) {
        // Implement error dialog display
    }
}
