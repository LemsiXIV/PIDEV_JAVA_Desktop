    module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
        requires java.mail;
        requires android.json;
        requires spring.security.core;
        requires jbcrypt;
        requires com.google.gson;

        opens com.webandit.webuild.models to javafx.base;
        exports com.webandit.webuild.controllers;
        exports com.webandit.webuild.controllers.back;

    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.controllers.back to javafx.fxml;

    exports com.webandit.webuild.test;
    opens com.webandit.webuild.test to javafx.fxml;
        exports com.webandit.webuild.controllers.front;
        opens com.webandit.webuild.controllers.front to javafx.fxml;


    }