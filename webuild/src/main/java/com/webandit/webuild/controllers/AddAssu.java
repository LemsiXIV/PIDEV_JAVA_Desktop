package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.scene.Node;



        import com.webandit.webuild.models.Assurance;
        import com.webandit.webuild.services.ServiceAssurance;
        import com.webandit.webuild.utils.DBConnection;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.control.ListView;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
        import java.sql.*;
        import java.util.ResourceBundle;

public class AddAssu  implements Initializable {
    private Connection cnx;
    int id = 0;
    @FXML
    private Button viewlist;
    @FXML
    private Button delAssu;
    @FXML
    private Button UpAssu;
    @FXML
    private Button addAssu;
    @FXML
    private TableColumn<Assurance, String> colage;

    @FXML
    private TableColumn<Assurance, String> coldesc;

    @FXML
    private TableColumn<Assurance, String> colfinanaciere;

    @FXML
    private TableColumn<Assurance, Integer> colfranchise;

    @FXML
    private TableColumn<Assurance, Integer> colid;

    @FXML
    private TableColumn<Assurance, String> colimg;

    @FXML
    private TableColumn<Assurance, String> colmedicale;

    @FXML
    private TableColumn<Assurance, String> colnom;

    @FXML
    private TableColumn<Assurance, Integer> colprime;


    @FXML
    private TableView<Assurance> tableAssu;

    @FXML
    private ListView<Assurance> listAssurance;

    @FXML
    private TextField txtCondition_financiere;

    @FXML
    private TextField txtCondition_medicale;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtFranchise;

    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrime;

    @FXML
    private TextField xtCondition_age;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void AddAssurance(ActionEvent event) {
        String nom = txtNom.getText();
        String description = txtDescription.getText();
        String image = txtImage.getText();
        String conditionAge = xtCondition_age.getText();
        String conditionMedicale = txtCondition_medicale.getText();
        String conditionFinanciere = txtCondition_financiere.getText();
        int franchise = Integer.parseInt(txtFranchise.getText());
        int prime = Integer.parseInt(txtPrime.getText());

        Assurance newAssurance = new Assurance();
        newAssurance.setNom(nom);
        newAssurance.setDescription(description);
        newAssurance.setImage(image);
        newAssurance.setCondition_age(conditionAge);
        newAssurance.setCondition_medicale(conditionMedicale);
        newAssurance.setCondition_financiere(conditionFinanciere);
        newAssurance.setFranchise(franchise);
        newAssurance.setPrime(prime);

        try {
            ServiceAssurance serviceAssurance = new ServiceAssurance();
            // Utilisez le service pour ajouter l'assurance
            serviceAssurance.insertOne(newAssurance);
            System.out.println("Assurance ajoutée avec succès !");

            // Effacez les champs de texte après l'ajout
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'erreur de manière appropriée (par exemple, affichez un message d'erreur à l'utilisateur)
        }
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
        txtImage.clear();
        xtCondition_age.clear();
        txtCondition_medicale.clear();
        txtCondition_financiere.clear();
        txtFranchise.clear();
        txtPrime.clear();
    }


    @FXML
    void getData(MouseEvent event) {
        Assurance assurance = tableAssu.getSelectionModel().getSelectedItem();
        id = assurance.getId();
        txtNom.setText(assurance.getNom());
        txtDescription.setText(assurance.getDescription());
        txtImage.setText(assurance.getImage());
        xtCondition_age.setText(assurance.getCondition_age());
        txtCondition_medicale.setText(assurance.getCondition_medicale());
        txtCondition_financiere.setText(assurance.getCondition_financiere());
        txtFranchise.setText(String.valueOf(assurance.getFranchise()));
        txtPrime.setText(String.valueOf(assurance.getPrime()));

    }

    @FXML
    void BackToList(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/ShowAssurance.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();



    }
}


