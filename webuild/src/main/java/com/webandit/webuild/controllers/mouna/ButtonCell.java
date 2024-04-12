package com.webandit.webuild.controllers.mouna;

import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.HBox;

public class ButtonCell<S, T> extends TreeTableCell<S, T> {

    private final Button deleteButton = new Button("Supprimer");
    private final Button editButton = new Button("Modifier");

    public ButtonCell() {
        deleteButton.setOnAction(event -> {
            // Logique de suppression
            getTreeTableView().getTreeItem(getIndex()).getValue();
        });

        editButton.setOnAction(event -> {
            // Logique de modification
            getTreeTableView().getTreeItem(getIndex()).getValue();
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            HBox buttons = new HBox(deleteButton, editButton);
            setGraphic(buttons);
        }
    }
}

