package com.webandit.webuild.test;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
public class MapWebViewExample extends Application{
    @Override
    public void start(Stage primaryStage) {
        // Create a WebView
        WebView webView = new WebView();

        // Load the HTML file with Google Maps script
        webView.getEngine().load(getClass().getResource("map.html").toExternalForm());



        // Create the scene
        Scene scene = new Scene(webView, 800, 600);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Map WebView Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
