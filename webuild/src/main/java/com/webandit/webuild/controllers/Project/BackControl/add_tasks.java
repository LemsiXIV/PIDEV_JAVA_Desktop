package com.webandit.webuild.controllers.Project.BackControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceChantier;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class add_tasks {

    ServiceTasks ps = new ServiceTasks();
    ServiceChantier cs = new ServiceChantier();
    private Stage stage; // Declare Stage

    @FXML
    private ChoiceBox<Chantier> ts_chantierChoiceBox;
    @FXML
    private TextArea ts_description;

    @FXML
    private DatePicker ts_due;

    @FXML
    private TextField ts_name;

    @FXML
    private TextField ts_priority;

    @FXML
    private TextField ts_status;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }


    // Method to perform validation
    public boolean validateFields() {
        boolean isValid = true;

        if (!isValidName(ts_name.getText())) {
            displayError(ts_name, "Le nom de la tâche doit contenir uniquement des lettres");
            isValid = false;
        } else {
            displaySuccess(ts_name);
        }

        if (!isValidPriority(ts_priority.getText())) {
            displayError(ts_priority, "La priorité doit être 'High', 'Medium' ou 'Low'");
            isValid = false;
        } else {
            displaySuccess(ts_priority);
        }



        if (!isValidDescription(ts_description.getText())) {
            displayError(ts_description, "La description doit avoir entre 10 et 255 caractères");
            isValid = false;
        } else {
            displaySuccess(ts_description);
        }

        // Add validation for other fields

        return isValid;
    }

    // Method to validate task name
    private boolean isValidName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }

    // Method to validate task priority
    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("High") || priority.equalsIgnoreCase("Medium") || priority.equalsIgnoreCase("Low");
    }

    // Method to validate task status
    private boolean isValidStatus(String status) {
        return status.equals("1") || status.equals("0");
    }

    // Method to validate task description length
    private boolean isValidDescription(String description) {
        return description.length() >= 10 && description.length() <= 255;
    }

    // Method to display error message
    private void displayError(TextField field, String errorMessage) {
        field.getStyleClass().add("error-field");
        showAlert("Erreur de validation", errorMessage);
    }

    private void displayError(TextArea field, String errorMessage) {
        field.getStyleClass().add("error-field");
        showAlert("Erreur de validation", errorMessage);
    }

    // Method to display success message
    private void displaySuccess(TextField field) {
        field.getStyleClass().remove("error-field");
    }

    private void displaySuccess(TextArea field) {
        field.getStyleClass().remove("error-field");
    }

    // Method to display alert


  public void setStage(Stage stage) {
      this.stage = stage;
  }

    public void Add_task(ActionEvent event) {
      try {
          // Perform validation
          if (validateFields()) {
              LocalDate localDate = ts_due.getValue();
              java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

              Chantier selectedChantier = ts_chantierChoiceBox.getValue(); // Get selected Chantier
              if (selectedChantier == null) {
                  showAlert("Error", "Please select a Chantier");
                  return;
              }

              Tasks task = new Tasks(ts_name.getText(), ts_priority.getText(), 0, ts_description.getText(), sqlDate, selectedChantier);
              ps.insertOne(task);
              Notifications notification = Notifications.create()
                      .title("Title")
                      .text("Your notification message")
                      .graphic(null) // You can set a graphic if needed
                      .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                      .position(Pos.BOTTOM_RIGHT);
              notification.show();
              stage.close();
          }
      } catch (SQLException | NumberFormatException e) {
          showAlert("Erreur de saisie", "Erreur dans la saisie des données!");
      }
  }

    public void clear(ActionEvent event) {
        ts_name.clear();
        ts_priority.clear();
        ts_status.clear();
        ts_description.clear();
        ts_due.setValue(null);
        ts_chantierChoiceBox.setValue(null);
    }

    public void initialize() {
        try {
            // Load Chantiers into the ChoiceBox
            ts_chantierChoiceBox.setItems(FXCollections.observableArrayList(cs.selectAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
