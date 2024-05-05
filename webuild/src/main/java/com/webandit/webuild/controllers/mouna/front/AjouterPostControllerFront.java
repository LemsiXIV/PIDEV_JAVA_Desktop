package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.controllers.mouna.objettmouna;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.PostService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterPostControllerFront implements Initializable {
    @FXML
    private VBox Post_Ligne_Info;

    PostService ps = new PostService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Post> posts = null;
        try {
            posts = ps.read();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< posts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/forum/objet_post.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                objettmouna obj = fxmlLoader.getController();
                obj.setData(posts.get(i));
                obj.setId(posts.get(i).getId());
                Post_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}