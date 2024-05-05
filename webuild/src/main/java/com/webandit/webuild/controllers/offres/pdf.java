package com.webandit.webuild.controllers.offres;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
public class pdf {
    @FXML
    private WebView webView;

    public void initialize() {
        // Initialize the WebView, if needed
    }

    public void loadPDF(String pdfURL) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(pdfURL);
    }
}
