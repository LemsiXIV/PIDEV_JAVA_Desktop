module com.webandit.webuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Open packages to JavaFX modules
    opens com.webandit.webuild.controllers to javafx.fxml;
    opens com.webandit.webuild.models to javafx.fxml, javafx.base; // Open to javafx.base module as well

    // Export packages
    exports com.webandit.webuild.controllers;
    exports com.webandit.webuild.test;

    // Open test package to JavaFX modules
    opens com.webandit.webuild.test to javafx.fxml;
}
