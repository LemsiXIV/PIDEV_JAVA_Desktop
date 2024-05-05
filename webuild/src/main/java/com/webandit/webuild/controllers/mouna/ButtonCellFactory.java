package com.webandit.webuild.controllers.mouna;

import com.webandit.webuild.models.Post;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.function.Consumer;

public class ButtonCellFactory implements Callback<TreeTableColumn<Post, Void>, TreeTableCell<Post, Void>> {

    private final TreeTableView<Post> tvc;
    private final Consumer<Post> deleteCallback;
    private final Consumer<Post> editCallback;

    public ButtonCellFactory(TreeTableView<Post> tvc, Consumer<Post> deleteCallback, Consumer<Post> editCallback) {
        this.tvc = tvc;
        this.deleteCallback = deleteCallback;
        this.editCallback = editCallback;
    }

    @Override
    public TreeTableCell<Post, Void> call(TreeTableColumn<Post, Void> param) {
        return new TreeTableCell<>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Post item = getTreeTableRow().getItem();
                    deleteCallback.accept(item);
                });

                editButton.setOnAction(event -> {
                    Post item = getTreeTableRow().getItem();
                    editCallback.accept(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(deleteButton, editButton));
                }
            }
        };
    }
}

