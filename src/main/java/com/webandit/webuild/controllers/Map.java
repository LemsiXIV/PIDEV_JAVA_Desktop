package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceOffre;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;
import netscape.javascript.JSObject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Map {

    @FXML
    private WebView webView;

    private final ServiceOffre serviceOffres;

    public Map() {
        this.serviceOffres = new ServiceOffre();
    }

    public void initialize() {
        List<Offre> offresList;
        try {
            offresList = serviceOffres.selectAll();
            displayOffresOnMap(offresList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private void displayOffresOnMap(List<Offre> offresList) {
        // Convert the list of Offre objects to a JSON string
        String offresJson = convertOffresListToJson(offresList);
        System.out.println("Offres JSON: " + offresJson); // Print out the JSON data
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/html/map.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaBridge", new JavaBridge());

                // Pass the JSON data to the JavaScript function
                webEngine.executeScript("displayOffres(" + offresJson + ")");
            }
        });
    }


    // Convert the list of Offre objects to a JSON string
    private String convertOffresListToJson(List<Offre> offresList) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < offresList.size(); i++) {
            Offre offre = offresList.get(i);
            jsonBuilder.append("{")
                    .append("\"lat\":").append(offre.getLatitude()).append(",")
                    .append("\"lng\":").append(offre.getLongitude()).append(",")
                    .append("\"title\":\"").append(offre.getTitle()).append("\"")
                    .append("}");
            if (i < offresList.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    public class JavaBridge {
        public void showAlert(String message) {
            System.out.println("From JavaScript: " + message);
        }
    }
}
