module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires javafx.web;


    exports com.webandit.webuild.controllers;
    exports com.webandit.webuild.controllers.mouna;
    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.controllers.mouna to javafx.fxml;
    opens com.webandit.webuild.controllers.Project.FrontControl to javafx.fxml;
    opens com.webandit.webuild.controllers.Project.BackControl to javafx.fxml;
    opens com.webandit.webuild.test to javafx.fxml;
    opens com.webandit.webuild.models;
    exports com.webandit.webuild.test;
    exports com.webandit.webuild.controllers.Project.BackControl;
    exports com.webandit.webuild.controllers.Project.FrontControl;



}