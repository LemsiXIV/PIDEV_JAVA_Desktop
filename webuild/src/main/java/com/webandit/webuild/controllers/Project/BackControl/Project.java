package com.webandit.webuild.controllers.Project.BackControl;

import com.webandit.webuild.controllers.Project.FrontControl.objject;
import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceChantier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class Project {
    ServiceChantier ps = new ServiceChantier();
    int iduser = SessionManagement.getInstance().getId();
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
    private TableColumn<Chantier, Void> ch_view_action;

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
            int idUserVal = SessionManagement.getInstance().getId();
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

            Chantier ch = new Chantier(ch_nom.getText(), ch_description.getText(), sqlDate, Float.parseFloat(ch_remuneration.getText()),idUserVal);
            System.out.println(ch);
            ps.insertOne(ch);
            afficherChantier();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Erreur de saisie", "Erreur dans la saisie des données !");
        }
    }



    public void afficherChantier() {
        try {
            ch_view.getItems().setAll(ps.selectAllByUser(iduser)); // Accessing ServiceChantier methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des chantiers!");
        }
    }

    @FXML
    void initialize() throws SQLException{
        ch_view_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ch_view_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ch_view_rem.setCellValueFactory(new PropertyValueFactory<>("remuneration"));
        ch_view_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        afficherChantier(); //refrech el tableview




        // Column bindings
        ch_view_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ch_view_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ch_view_rem.setCellValueFactory(new PropertyValueFactory<>("remuneration"));
        ch_view_date.setCellValueFactory(new PropertyValueFactory<>("date"));



        // Enable editing for each cell
        ch_view_nom.setCellFactory(TextFieldTableCell.forTableColumn());
        ch_view_desc.setCellFactory(TextFieldTableCell.forTableColumn());
        ch_view_rem.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        ch_view_date.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        // Handle edit commit events
        ch_view_nom.setOnEditCommit(event -> {
            try {
                handleEditCommit(event, "nom");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ch_view_desc.setOnEditCommit(event -> {
            try {
                handleEditCommit(event, "description");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ch_view_rem.setOnEditCommit(event -> {
            try {
                handleEditCommit(event, "remuneration");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ch_view_date.setOnEditCommit(event -> {
            try {
                handleEditCommit(event, "date");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Update button action
        ch_view_action.setCellFactory(param -> new TableCell<>() {
            //    private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");

            {


                deleteButton.setOnAction(event -> {
                    Chantier chantier = getTableView().getItems().get(getIndex());
                    int var = chantier.getId();
                    if (var != 0 ) {
                        try {
                            ps.deleteOne(var);
                            afficherChantier();
                            Notifications notification = Notifications.create()
                                    .title("DB_Updated")
                                    .text("TASK DELETED SUCCUSFULY")
                                    .graphic(null) // You can set a graphic if needed
                                    .hideAfter(Duration.seconds(5))// Set how long the notification will be shown
                                    .darkStyle()
                                    .position(Pos.BOTTOM_RIGHT);

                            notification.show();

                        } catch (SQLException e) {
                            showAlert("Erreur", "Erreur lors de la suppression de la tache !");
                        }
                    } else {

                        Notifications notification = Notifications.create()
                                .title("Nothing selected")
                                .text("Aucun tache sélectionné")
                                .graphic(null) // You can set a graphic if needed
                                .hideAfter(Duration.seconds(5))// Set how long the notification will be shown
                                .darkStyle()
                                .position(Pos.BOTTOM_RIGHT);

                        notification.show();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(/*updateButton, */deleteButton);
                    setGraphic(buttonBox);
                }
            }
        });

        // Refresh the TableView
        afficherChantier();

    }

    // Method to handle edit commit events
    private void handleEditCommit(TableColumn.CellEditEvent<Chantier, ?> event, String propertyName) throws IOException {
        Chantier chantier = event.getRowValue();
        if (propertyName.equals("date")) {
            chantier.setDate(java.sql.Date.valueOf(event.getNewValue().toString()));
        } else {
            switch (propertyName) {
                case "nom":
                    chantier.setNom(event.getNewValue().toString());
                    break;
                case "description":
                    chantier.setDescription(event.getNewValue().toString());
                    break;
                case "remuneration":
                    chantier.setRemuneration(Float.parseFloat(event.getNewValue().toString()));
                    break;
            }
        }

        updateChantierr(chantier); // Corrected method call
    }

    @FXML
    void resetFields(ActionEvent event) {
        ch_nom.clear();
        ch_description.clear();
        ch_remuneration.clear();
        ch_date.setValue(null);
    }



    private void updateChantierr(Chantier chantier) {
        try {
            ps.updateOne(chantier); // Update the modified task using the service

            Notifications notification = Notifications.create()
                    .title("Success")
                    .text("Task updated!")
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5))// Set how long the notification will be shown
                    .darkStyle()
                    .position(Pos.CENTER);

            notification.show();

            afficherChantier(); // Refresh the table after updating the task
        } catch (SQLException e) {
            Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Error updating the task: " + e.getMessage())
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5))// Set how long the notification will be shown
                    .darkStyle()
                    .position(Pos.CENTER);

            notification.show();

            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
    @FXML
    private StackPane contentArea;
    public void Tasks(ActionEvent actionEvent) throws IOException, SQLException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/Back/Tasks.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);


    }

}
