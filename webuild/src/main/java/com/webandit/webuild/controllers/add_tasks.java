package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceChantier;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class add_tasks {

    ServiceTasks ps = new ServiceTasks();
    ServiceChantier cs = new ServiceChantier();

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
  /*  public void Add_task(ActionEvent event) {
        try {
            LocalDate localDate = ts_due.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            Tasks ch = new Tasks(ts_name.getText(), ts_priority.getText(), Integer.parseInt(ts_status.getText()), ts_description.getText(), sqlDate, null) ;

            ps.insertOne(ch); // Accessing ServiceChantier methods via 'ps' instance
            // afficherChantier(); // Refresh the table after adding a chantier
        } catch (SQLException | NumberFormatException e) {
            showAlert("Erreur de saisie", "Erreur dans la saisie des données!");
        }
    }*/
  public void Add_task(ActionEvent event) {
      try {
          LocalDate localDate = ts_due.getValue();
          java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

          Chantier selectedChantier = ts_chantierChoiceBox.getValue(); // Get selected Chantier
          if (selectedChantier == null) {
              showAlert("Error", "Please select a Chantier");
              return;
          }

          Tasks task = new Tasks(ts_name.getText(), ts_priority.getText(), Integer.parseInt(ts_status.getText()), ts_description.getText(), sqlDate, selectedChantier);
          ps.insertOne(task);

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
