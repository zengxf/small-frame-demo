package cn.zxf.test.test_table;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 * Created by zengxf on 2019/8/28.
 */
public class TestTableController {

    int index = 1;
    @FXML
    TableView<Person> myTable;
    @FXML
    TableColumn<Person, String> checkColumn;
    @FXML
    TableColumn<Person, String> idColumn;
    @FXML
    TableColumn<Person, String> nameColumn;
    @FXML
    TableColumn<Person, String> ageColumn;

    ObservableList<Person> list = FXCollections.observableArrayList(item -> new Observable[]{item.selectedProperty()});


    @FXML
    private void initialize() {
        Callback<Integer, ObservableValue<Boolean>> callback = index -> list.get(index).selectedProperty();
        checkColumn.setCellFactory(cell -> new CheckBoxTableCell(callback));
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ageColumn.setCellValueFactory(cellData -> {
            Integer age = cellData.getValue().getAge();
            return new SimpleStringProperty(age.toString());
        });
        myTable.setItems(list);
    }

    public void onInitialize(ActionEvent event) {
        list.add(new Person(index++, "Test-" + index, 23 + index));
        list.add(new Person(index++, "Test 测试", 45 + index));
    }

    public void onAdd(ActionEvent event) {
        Person person = new Person(index++, "Zxf 峰", 22 + index);
        System.out.println("add: " + person);
        list.add(person);
    }

}
