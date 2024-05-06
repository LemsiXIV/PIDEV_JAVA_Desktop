package com.webandit.webuild.controllers.back;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import com.webandit.webuild.services.serviceUtilisateur;
import com.webandit.webuild.models.Utilisateur;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUser {

            @FXML
            private Slider activation;

            @FXML
            private TextField adresse;

            @FXML
            private TextField email;
    @FXML
    private Button deleteButton;


        @FXML
            private TextField nom;

            @FXML
            private TextField prenom;

            @FXML
            private TextField telephone;

            @FXML
            private Button updateButton;
    @FXML
    private ImageView backButton;
        serviceUtilisateur sp=new serviceUtilisateur();

          /*  @FXML
            private TextField statuus;
*/
            @FXML
            void active_false(SwipeEvent event) {

            }

            @FXML
            void update(MouseEvent event) {
                Utilisateur u = new Utilisateur(nom.getText(), prenom.getText(), Integer.parseInt(telephone.getText()), adresse.getText(), email.getText());
                // Update the user information in the database
                try {
                    sp.updateOne(u);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("updated");
                    alert.setHeaderText(null);
                    alert.setContentText("update successfully");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) updateButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    private String userEmail;

    public void setEmail(String email) {
        this.userEmail = email;
    }



    public void initialize(){


                try {
                    ResultSet resultSet=sp.selectData(userEmail);
                    if(resultSet.next()){
                        email.setText(resultSet.getString("email"));
                        nom.setText(resultSet.getString("nom"));
                        prenom.setText(resultSet.getString("prénom"));
                        adresse.setText(resultSet.getString("adresse"));
                        telephone.setText(String.valueOf(resultSet.getInt("téléphone")));
                        //statuus.setText(resultSet.getString("status"));

                    }else {

                        System.out.println("User not found!");
                    }

                }catch (SQLException e) {
                    e.printStackTrace();
                    // Handle exception, show error message, or log the error
                }
            }
    @FXML
    void delete(MouseEvent event) {
        Utilisateur u = new Utilisateur(nom.getText(), prenom.getText(), Integer.parseInt(telephone.getText()), adresse.getText(), email.getText());
        try {
            sp.deleteOne(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/front/hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}



