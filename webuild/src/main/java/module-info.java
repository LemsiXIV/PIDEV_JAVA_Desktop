    module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
        requires java.mail;


        exports com.webandit.webuild.controllers;
    opens com.webandit.webuild.controllers to javafx.fxml;
    exports com.webandit.webuild.test;
    opens com.webandit.webuild.test to javafx.fxml;


}