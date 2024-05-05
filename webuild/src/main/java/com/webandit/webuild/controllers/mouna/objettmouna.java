package com.webandit.webuild.controllers.mouna;




import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.PostService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class objettmouna implements Initializable {



    @FXML
    private Label   Post_name;

    @FXML
    private Label Post_description;

    @FXML
    private ImageView Post_img;

    PostService ps = new PostService();
    public void setData(Post post){

        System.out.println(post.getImg());
        Image imageprofile = new Image(post.getImg());
        Post_img.setImage(imageprofile);
        Post_name.setText(post.getTitre());
        Post_description.setText(post.getDescription());



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Manage(ActionEvent event) {

    }

    public void update(ActionEvent event) {

    }

    int id ;
    public void setId(int id) {
        this.id=id;

    }

    public void Delete(ActionEvent event ) {

        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                ps.delete(id); // Accessing ServiceChantier methods via 'ps' instance
                // Refresh the table after deleting a chantier
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }
}