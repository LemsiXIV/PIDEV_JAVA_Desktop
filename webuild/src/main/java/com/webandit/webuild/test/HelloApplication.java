package com.webandit.webuild.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    double x,y;
    @Override
    public void start(Stage stage) throws IOException {


     //  Parent root = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));
     Parent root = FXMLLoader.load(getClass().getResource("/fxml/Project/front/front_project.fxml"));


        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);
        });

        stage.setScene(new Scene(root, 1200 , 845));
        stage.setTitle("WeBuild");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}