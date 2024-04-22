package com.webandit.webuild.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.webandit.webuild.controllers.Utilisateur;

import java.io.IOException;

public class SessionManagement {
    private static SessionManagement instance;
    private String loggedInUserEmail;

    private SessionManagement() {
    }

    public static synchronized SessionManagement getInstance() {
        if (instance == null) {
            instance = new SessionManagement();
        }
        return instance;
    }

    public void setLoggedInUserEmail(String email) {
        this.loggedInUserEmail = email;
    }

    public String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public void clearSession() {
        loggedInUserEmail = null;

    }

}
