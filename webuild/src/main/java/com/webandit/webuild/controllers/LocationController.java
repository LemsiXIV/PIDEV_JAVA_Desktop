package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Materiel;
import com.webandit.webuild.models.Location;
import com.webandit.webuild.services.ServiceMateriel;
import com.webandit.webuild.services.ServiceLocation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LocationController {

    @FXML
    private TableColumn<Location, String> MaterielColumn;

    @FXML
    private TableColumn<Location, String> debutColumn;

    @FXML
    private TableColumn<Location, String> finColumn;

    @FXML
    private ChoiceBox<String> materielDrop;

    @FXML
    private Button addLocBtn;

    @FXML
    private TableView<Location> tabLocation;

    @FXML
    private TextField userField;

    @FXML
    private Button delLocBtn;

    @FXML
    private Button modLocBtn;

    @FXML
    private DatePicker debutPicker;

    @FXML
    private DatePicker finPicker;

    // Method to initialize the controller
    @FXML
    public void initialize() {
        userField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Vérifier si le nouveau texte contient uniquement des chiffres
                return change; // Autoriser le changement
            }
            return null; // Bloquer le changement
        }));
        try {
            ServiceMateriel sa = new ServiceMateriel();
            List<Materiel> materiels = sa.selectAll();
            populateMaterielChoiceBox(materiels);
            showLocations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        debutPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new date is before the current date
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // Show an error message
                showAlert("Error", "date non valide.");
                // Reset the DatePicker to the current date
                debutPicker.setValue(LocalDate.now());
            }
        });
        finPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new date is before the current date
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // Show an error message
                showAlert("Error", "date non valide.");
                // Reset the DatePicker to the current date
                finPicker.setValue(LocalDate.now());
            }
        });
    }

    // Method to fetch and display all locations in the TableView
    private void showLocations() throws SQLException {
        // Fetch all locations from the database
        ServiceLocation serviceLocation = new ServiceLocation();
        List<Location> locations = serviceLocation.selectAll();

        // Convert the list to an ObservableList
        ObservableList<Location> locationList = FXCollections.observableArrayList(locations);

        // Bind the ObservableList to the TableView
        tabLocation.setItems(locationList);

        MaterielColumn.setCellValueFactory(cellData -> {
            // Access the Materiel object associated with the Location
            Materiel materiel = cellData.getValue().getM();
            if (materiel != null) {
                // Return the name of the materiel
                return new SimpleStringProperty(materiel.getLibelle());
            } else {
                // If materiel is null, return an empty string
                return new SimpleStringProperty("");
            }
        });

        debutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_dString()));
        finColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_fString()));
    }

    @FXML
    void addLocation(ActionEvent event) {
        // Get values from the input fields
        String userStr = userField.getText();
        int user = Integer.parseInt(userStr);
        Date debut = Date.valueOf(debutPicker.getValue());
        Date fin = Date.valueOf(finPicker.getValue());
        String selectedMaterielName = materielDrop.getValue();

        // Check if an materiel name is selected
        if (selectedMaterielName == null) {
            // Handle the case where no materiel is selected (show error message, etc.)
            return;
        }

        // Retrieve the materiel instance based on its name
        Materiel selectedMateriel = null;
        try {
            ServiceMateriel sa = new ServiceMateriel();
            List<Materiel> materiels = sa.selectAll();
            for (Materiel materiel : materiels) {
                if (materiel.getLibelle().equals(selectedMaterielName)) {
                    selectedMateriel = materiel;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the selected materiel was found
        if (selectedMateriel == null) {
            // Handle the case where the selected materiel was not found (show error message, etc.)
            return;
        }

        // Set default values for user and status
        int defaultUser = 1;
        Integer defaultStatus = 0; // Using Integer instead of int to allow null values

        Location location = new Location();
        location.setM(selectedMateriel);
        location.setId_user(user);
        location.setDate_d(debut);
        location.setDate_f(fin);

        try {
            ServiceLocation serviceLocation = new ServiceLocation();
            // Use the service to add the location
            serviceLocation.insertOne(location);
            System.out.println("Location ajoutée avec succès !");
            showLocations();
            // Clear the input fields after adding the location
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }

    private void clearFields() {
        userField.clear();
        debutPicker.setValue(null);
        finPicker.setValue(null);
    }

    private void populateMaterielChoiceBox(List<Materiel> materiels) {
        ObservableList<String> materielNames = FXCollections.observableArrayList();
        for (Materiel materiel : materiels) {
            materielNames.add(materiel.getLibelle());
        }
        materielDrop.setItems(materielNames);
    }

    @FXML
    void delLocation(ActionEvent event) {
        // Get the selected location from the TableView
        Location selectedLocation = tabLocation.getSelectionModel().getSelectedItem();

        // Check if a location is selected
        if (selectedLocation == null) {
            // Handle the case where no location is selected (show error message, etc.)
            return;
        }

        try {
            ServiceLocation serviceLocation = new ServiceLocation();
            // Use the service to delete the location
            serviceLocation.deleteOne(selectedLocation);
            System.out.println("Location supprimée avec succès !");
            // Refresh the TableView after deletion
            showLocations();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }

    @FXML
    void getDataLocation(MouseEvent event) {
        // Get the selected location from the TableView
        Location selectedLocation = tabLocation.getSelectionModel().getSelectedItem();

        // Check if a location is selected
        if (selectedLocation != null) {
            // Set the data of the selected location to the input fields
            userField.setText(String.valueOf(selectedLocation.getId_user()));
            debutPicker.setValue(selectedLocation.getDate_d().toLocalDate());
            finPicker.setValue(selectedLocation.getDate_f().toLocalDate());

            // Set the selected materiel name in the ChoiceBox
            String selectedMaterielName = selectedLocation.getM().getLibelle();
            materielDrop.setValue(selectedMaterielName);
        }
    }

    @FXML
    void modLocation(ActionEvent event) {
        // Get the selected location from the TableView
        Location selectedLocation = tabLocation.getSelectionModel().getSelectedItem();

        // Check if a location is selected
        if (selectedLocation == null) {
            // Handle the case where no location is selected (show error message, etc.)
            return;
        }

        // Get values from the input fields
        String userStr = userField.getText();
        String selectedMaterielName = materielDrop.getValue(); // Get the selected materiel from the ChoiceBox

        // Convert user from String to int
        int user = Integer.parseInt(userStr);

        // Parse debut and fin strings into Date objects
        Date debut = Date.valueOf(debutPicker.getValue());
        Date fin = Date.valueOf(finPicker.getValue());

        // Check if an materiel name is selected
        if (selectedMaterielName == null) {
            // Handle the case where no materiel is selected (show error message, etc.)
            return;
        }

        // Retrieve the materiel instance based on its name
        Materiel selectedMateriel = null;
        try {
            ServiceMateriel sa = new ServiceMateriel();
            List<Materiel> materiels = sa.selectAll();
            for (Materiel materiel : materiels) {
                if (materiel.getLibelle().equals(selectedMaterielName)) {
                    selectedMateriel = materiel;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the selected materiel was found
        if (selectedMateriel == null) {
            // Handle the case where the selected materiel was not found (show error message, etc.)
            return;
        }

        // Set the modified values to the selected location
        selectedLocation.setM(selectedMateriel);
        selectedLocation.setId_user(user);
        selectedLocation.setDate_d(debut);
        selectedLocation.setDate_f(fin);

        try {
            ServiceLocation serviceLocation = new ServiceLocation();
            // Use the service to update the location in the database
            serviceLocation.updateOne(selectedLocation);
            System.out.println("Location modifiée avec succès !");
            // Refresh the TableView after modification
            showLocations();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately (e.g., show an error message to the user)
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
