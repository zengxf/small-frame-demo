package cn.zxf.test.test_table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by zengxf on 2019/8/28.
 */
public class TestTableController {

    @FXML
    TableView<Person> myTable;
    @FXML
    TableColumn<Person, String> nameColumn;
    @FXML
    TableColumn<Person, String> ageColumn;

    ObservableList<Person> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ageColumn.setCellValueFactory(cellData -> {
            Integer age = cellData.getValue().getAge();
            return new SimpleStringProperty(age.toString());
        });
        myTable.setItems(list);
    }

    public void onInitialize(ActionEvent event) {
        list.add(new Person("Hans", 23));
        list.add(new Person("Ruth", 45));
    }

    public void onAdd(ActionEvent event) {
        Person person = new Person("Zxf", 22);
        System.out.println("add: " + person);
        list.add(person);
    }

}
