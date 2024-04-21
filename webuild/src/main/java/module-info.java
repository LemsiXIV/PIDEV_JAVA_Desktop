module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports com.webandit.webuild.controllers;
    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.models to javafx.base;
    exports com.webandit.webuild.test;
    opens com.webandit.webuild.test to javafx.fxml;


}