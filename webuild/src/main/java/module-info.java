module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports com.webandit.webuild.controllers;
    exports com.webandit.webuild.controllers.mouna;
    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.controllers.mouna to javafx.fxml;
    opens com.webandit.webuild.models;
    exports com.webandit.webuild.test;
    opens com.webandit.webuild.test to javafx.fxml;


}