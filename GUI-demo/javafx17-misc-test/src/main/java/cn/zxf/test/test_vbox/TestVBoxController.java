package cn.zxf.test.test_vbox;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/29.
 */
public class TestVBoxController {

    int index = 10;

    @FXML
    VBox vbox;

    public void onAdd(ActionEvent event) {
        ObservableList<Node> panes = vbox.getChildren();

        Label show = new Label("============> " + index);
        show.setLayoutX(50);
        show.setLayoutY(50);

        AnchorPane content = new AnchorPane();
        content.prefHeight(180);
        content.prefWidth(200);
        content.getChildren().add(show);

        TitledPane add = new TitledPane("Add-Test-" + index, content);
        panes.add(add);

        index++;
    }

    public void onExpandAll(ActionEvent event) {
        ObservableList<Node> panes = vbox.getChildren();
        panes.forEach(pane -> {
            if (pane instanceof TitledPane tp)
                tp.setExpanded(true);
        });
    }

    public void onFoldAll(ActionEvent event) {
        ObservableList<Node> panes = vbox.getChildren();
        panes.forEach(pane -> {
            if (pane instanceof TitledPane tp)
                tp.setExpanded(false);
        });
    }

}
