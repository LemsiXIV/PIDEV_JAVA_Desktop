package com.webandit.webuild.controllers;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.webandit.webuild.models.Assurance;

public class AssuranceCardController {
    @FXML
    private Label titre;

    @FXML
    private Label description;

    @FXML
    private Label conditions;

    @FXML
    private Label franchise;

    @FXML
    private Label prime;

    private Assurance assurance;

    public void setData(Assurance assurance) {
        this.assurance = assurance;
        titre.setText(assurance.getNom());
        description.setText(assurance.getDescription());
        conditions.setText("Conditions: " + assurance.getCondition_age() + ", " + assurance.getCondition_medicale() + ", " + assurance.getCondition_financiere());
        franchise.setText("Franchise: " + assurance.getFranchise());
        prime.setText("Prime: " + assurance.getPrime());
    }
}
