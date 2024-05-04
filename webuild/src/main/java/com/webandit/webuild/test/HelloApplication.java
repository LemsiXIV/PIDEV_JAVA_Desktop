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


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);
        });

        stage.setScene(new Scene(root, 700 , 500));
        stage.setTitle("WeBuild");
        stage.show();
    }
    private void switchToSignIn(Stage stage) throws IOException {
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
        Scene signInScene = new Scene(signInRoot, 700, 500);
        stage.setScene(signInScene);
    }
   /* private void changeToReset(Stage stage) throws IOException {
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/fxml/Reset-pwd.fxml"));
        Scene changeToReset = new Scene(signInRoot, 700, 500);
        stage.setScene(changeToReset);
    }
*/
    public static void main(String[] args) {
        launch();
    }
}