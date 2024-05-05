module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires twilio;
    requires javafx.base;
    requires com.google.zxing;
    requires stripe.java;
    requires javafx.swing;
    requires java.mail;
    requires jdk.jsobject;
    requires android.json;


    exports com.webandit.webuild.controllers;
    exports com.webandit.webuild.controllers.mouna;
    exports com.webandit.webuild.controllers.mouna.front;
    exports com.webandit.webuild.controllers.Project;
    exports com.webandit.webuild.controllers.Assurance.front;
    exports com.webandit.webuild.controllers.Assurance.back;
    exports com.webandit.webuild.controllers.Materiel.Back;
    exports com.webandit.webuild.controllers.Materiel.Front;
    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.controllers.Assurance.front to javafx.fxml;
    opens com.webandit.webuild.controllers.Assurance.back to javafx.fxml;
    opens com.webandit.webuild.controllers.mouna to javafx.fxml;
    opens com.webandit.webuild.controllers.mouna.front to javafx.fxml;
    opens com.webandit.webuild.controllers.Project.FrontControl to javafx.fxml;
    opens com.webandit.webuild.controllers.Project.BackControl to javafx.fxml;
    opens com.webandit.webuild.controllers.Project to javafx.fxml;
    opens com.webandit.webuild.controllers.Materiel.Back to javafx.fxml;
    opens com.webandit.webuild.controllers.Materiel.Front to javafx.fxml;
    opens com.webandit.webuild.controllers.offres to javafx.fxml;
    opens com.webandit.webuild.controllers.back to javafx.fxml;
    opens com.webandit.webuild.controllers.front to javafx.fxml;
    opens com.webandit.webuild.test to javafx.fxml;
    opens com.webandit.webuild.models;
    exports com.webandit.webuild.test;
    exports com.webandit.webuild.controllers.Project.BackControl;
    exports com.webandit.webuild.controllers.Project.FrontControl;
    exports com.webandit.webuild.controllers.offres;



}