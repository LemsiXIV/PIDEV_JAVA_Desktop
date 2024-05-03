package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.time.LocalDate;

public class add_project {

    ServiceChantier ps = new ServiceChantier();

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextArea Project_description;

    @FXML
    private DatePicker Project_due;

    @FXML
    private TextField Project_name;

    @FXML
    private TextField Project_rem;







    @FXML
    void Add_Project(ActionEvent event) throws SQLException {
        try {
            LocalDate localDate = Project_due.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);


            Chantier ch = new Chantier(Project_name.getText(),Project_description.getText(),sqlDate,Float.parseFloat(Project_rem.getText()));
            ps.insertOne(ch);
            stage.close();
            Notifications notification = Notifications.create()
                    .title("Project Added")
                    .text("Your Project name "+Project_name.getText()+" is added Succesfully")
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            System.out.println(ch);
    } catch (SQLException | NumberFormatException e) {
            Notifications notification = Notifications.create()
                    .title("ADD Chantier")
                    .text("Error when Collecting the data ")
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
    }
    }

    @FXML
    void clear(ActionEvent event) {
        Project_description.clear();
        Project_name.clear();
        Project_due.setValue(null);
        Project_rem.clear();
    }


}
