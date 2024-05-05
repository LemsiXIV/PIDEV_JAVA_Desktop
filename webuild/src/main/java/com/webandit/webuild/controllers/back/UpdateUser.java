package com.webandit.webuild.controllers.back;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import com.webandit.webuild.services.serviceUtilisateur;
import com.webandit.webuild.models.Utilisateur;
import javafx.scene.layout.StackPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUser {
    private String userEmail;

            @FXML
            private StackPane contentArea;
            @FXML
            private Slider activation;

            @FXML
            private TextField adresse;

            @FXML
            private TextField email;


        @FXML
            private TextField nom;

            @FXML
            private TextField prenom;

            @FXML
            private TextField telephone;

            @FXML
            private Button updateButton;
        Utilisateur user= new Utilisateur();
        serviceUtilisateur sp=new serviceUtilisateur();

            @FXML
            private TextField statuus;

            @FXML
            void active_false(SwipeEvent event) {

            }

            @FXML
            void update(MouseEvent event) {

            }
            public void initialize(){
                try {
                    ResultSet resultSet=sp.selectData(userEmail);
                    if(resultSet.next()){
                        email.setText(resultSet.getString("email"));
                        nom.setText(resultSet.getString("nom"));
                        prenom.setText(resultSet.getString("prenom"));
                        adresse.setText(resultSet.getString("adresse"));
                        telephone.setText(String.valueOf(resultSet.getInt("telephone")));

                    }else {

                        System.out.println("User not found!");
                    }

                }catch (SQLException e) {
                    e.printStackTrace();
                    // Handle exception, show error message, or log the error
                }
            }

    public void setEmail(String userEmail) {
                this.userEmail=userEmail;
    }
}



