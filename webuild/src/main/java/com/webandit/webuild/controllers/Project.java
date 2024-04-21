package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Project {
    ServiceChantier ps = new ServiceChantier();

    @FXML
    private DatePicker ch_date;

    @FXML
    private Button ch_delete;

    @FXML
    private TextField ch_description;

    @FXML
    private TextField ch_nom;

    @FXML
    private TextField ch_remuneration;

    @FXML
    private Button ch_reset;

    @FXML
    private Button ch_save;

    @FXML
    private Button ch_update;

    @FXML
    private TableView<Chantier> ch_view;
    @FXML
    private TableColumn<Chantier, java.util.Date> ch_view_date;

    @FXML
    private TableColumn<Chantier, String> ch_view_desc;

    @FXML
    private TableColumn<Chantier, String> ch_view_nom;

    @FXML
    private TableColumn<Chantier, Float> ch_view_rem;
    @FXML
    private TableColumn<Chantier, Float> ch_view_action;

    // Utility method to show alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    // Method to perform validation for the Chantier name
    private boolean isValidChantierName(String name) {
        return Pattern.matches("[a-zA-Z]{5,20}", name);
    }

    // Method to perform validation for the Chantier remuneration
    private boolean isValidChantierRemuneration(String remuneration) {
        return Pattern.matches("\\d+(\\.\\d+)?", remuneration);
    }

    // Method to perform validation for the Chantier description
    private boolean isValidChantierDescription(String description) {
        return description.length() >= 7 && description.length() <= 50;
    }

    // Method to perform validation for the Chantier date
    private boolean isValidChantierDate(LocalDate date) {
        // Check if the date is not null and is after or equal to today's date
        return date != null && !date.isBefore(LocalDate.now());
    }

    // Method to display error message
    private void displayError(TextField field, String errorMessage) {
        field.getStyleClass().add("error-field");
        showAlert("Erreur de validation", errorMessage);
    }

    // Method to remove error style
    private void removeErrorStyle(TextField field) {
        field.getStyleClass().remove("error-field");
    }

    @FXML
    void addchantier(ActionEvent event) {
        try {
            LocalDate localDate = ch_date.getValue();
            if (!isValidChantierDate(localDate)) {
                showAlert("Erreur de saisie", "La date doit être ultérieure à aujourd'hui !");
                return;
            }

            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            if (!isValidChantierName(ch_nom.getText())) {
                displayError(ch_nom, "Le nom du chantier doit contenir uniquement des lettres et avoir une longueur entre 5 et 20 caractères !");
                return;
            } else {
                removeErrorStyle(ch_nom);
            }

            if (!isValidChantierRemuneration(ch_remuneration.getText())) {
                displayError(ch_remuneration, "La rémunération doit être un nombre flottant !");
                return;
            } else {
                removeErrorStyle(ch_remuneration);
            }

            Chantier ch = new Chantier(ch_nom.getText(), ch_description.getText(), sqlDate, Float.parseFloat(ch_remuneration.getText()));

            ps.insertOne(ch);
            afficherChantier();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Erreur de saisie", "Erreur dans la saisie des données !");
        }
    }

    @FXML
    void deleteChantier(ActionEvent event) {
        Chantier selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            try {
                ps.deleteOne(selectedChantier.getId()); // Accessing ServiceChantier methods via 'ps' instance
                afficherChantier(); // Refresh the table after deleting a chantier
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression du chantier!");
            }
        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }

    public void afficherChantier() {
        try {
            ch_view.getItems().setAll(ps.selectAll()); // Accessing ServiceChantier methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des chantiers!");
        }
    }

    @FXML
    void initialize() {
        ch_view_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ch_view_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ch_view_rem.setCellValueFactory(new PropertyValueFactory<>("remuneration"));
        ch_view_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ch_view_action.setCellValueFactory(new PropertyValueFactory<>("Action"));
        afficherChantier(); //refrech el tableview
    }


    @FXML
    void resetFields(ActionEvent event) {
        ch_nom.clear();
        ch_description.clear();
        ch_remuneration.clear();
        ch_date.setValue(null);
    }
    @FXML
    void selectChantier(ActionEvent event) {
        Chantier selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            ch_nom.setText(selectedChantier.getNom());
            ch_description.setText(selectedChantier.getDescription());
            ch_remuneration.setText(String.valueOf(selectedChantier.getRemuneration()));
            ch_date.setValue(selectedChantier.getDate().toLocalDate());
        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }

    @FXML
    void updateChantier(ActionEvent event) {
        Chantier selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            try {
                // Update the selected chantier object with the modified data
                selectedChantier.setNom(ch_nom.getText());
                selectedChantier.setDescription(ch_description.getText());
                selectedChantier.setRemuneration(Float.parseFloat(ch_remuneration.getText()));
                selectedChantier.setDate(java.sql.Date.valueOf(ch_date.getValue()));

                // Call the updateOne function from the service to update the database
                ps.updateOne(selectedChantier);

                // Refresh the table view after updating
                afficherChantier();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour du chantier!");
            }
        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }
    @FXML
    private StackPane contentArea;
    public void Tasks(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Tasks.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
