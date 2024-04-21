package com.webandit.webuild.controllers;


import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class frontassurance implements Initializable {
    private Connection cnx;
    private Assurance selectedAssurance;

    @FXML
    private Button choice;

    @FXML
    private ListView<Assurance> lista;

    @FXML
    void addchoice(ActionEvent event) throws IOException {
        // Get the selected assurance's name
        Assurance selectedAssurance = lista.getSelectionModel().getSelectedItem();
        String selectedAssuranceName = selectedAssurance.getNom();

        // Load the demandefront.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/demandefront.fxml"));
        Parent demandefrontParent = loader.load();

        // Pass the selected assurance's name to the demandefront controller
        Demandefront demandefrontController = loader.getController();
        demandefrontController.setSelectedAssuranceName(selectedAssuranceName);

        // Set up the scene and stage
        Scene demandefrontScene = new Scene(demandefrontParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(demandefrontScene);
        window.show();
    }


    @FXML
    void selectA(MouseEvent event) {
       Assurance SelectedAssurance = lista.getSelectionModel().getSelectedItem();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showass();
    }
    public void showass() {
        lista.setItems(getAssurances());
    }
    public ObservableList<Assurance> getAssurances() {
        ObservableList<Assurance> assurances = FXCollections.observableArrayList();
        String query = "SELECT * FROM assurance";
        cnx = DBConnection.getInstance().getCnx();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Assurance a = new Assurance();
                a.setNom(rs.getString("nom"));
                a.setDescription(rs.getString("description"));
                a.setFranchise(rs.getInt("franchise"));
                a.setPrime(rs.getInt("prime"));
                assurances.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assurances;
    }

}





