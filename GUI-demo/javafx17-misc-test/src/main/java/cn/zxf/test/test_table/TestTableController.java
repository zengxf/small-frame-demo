package cn.zxf.test.test_table;

import cn.zxf.test.AbstractMain;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Created by zengxf on 2019/8/28.
 */
@Slf4j
public class TestTableController {

    @FXML
    Pane myPane;
    @FXML
    TableView<Person> myTable;
    @FXML
    TableColumn<Person, String> checkColumn;
    @FXML
    TableColumn<Person, Number> idColumn;
    @FXML
    TableColumn<Person, String> nameColumn;
    @FXML
    TableColumn<Person, Number> ageColumn;
    @FXML
    TableColumn<Person, String> ex1Column;
    @FXML
    TableColumn<Person, String> ex2Column;

    int index = 0;
    ObservableList<Person> list = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // 泛型编译不过，要单独写出来
        Callback<Integer, ObservableValue<Boolean>> callback = index -> list.get(index).selectedProperty();
        checkColumn.setCellFactory(cell -> new CheckBoxTableCell(callback));

        idColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()));
        idColumn.prefWidthProperty().bind(myTable.widthProperty().multiply(0.15));

        // 设置编辑，首先 Table 要启用编辑，编辑完要按 Entry 键才会提交
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(this::editNameCommit); // 代码绑定
        nameColumn.prefWidthProperty().bind(myTable.widthProperty().multiply(0.20));

        ageColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getAge()));
        ageColumn.setCellFactory(cell ->
                new TextFieldTableCell<>() {
                    @Override
                    public void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            this.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
                            this.setTextFill(Color.RED);
                            if (item.intValue() > 30)
                                this.setTextFill(Color.BLUEVIOLET);
                            setText(item.toString());
                        }
                    }
                }
        );
        ageColumn.prefWidthProperty().bind(myTable.widthProperty().multiply(0.15));

        ex1Column.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEx1()));
        ex1Column.prefWidthProperty().bind(myTable.widthProperty().multiply(0.20));
        ex2Column.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEx2()));
        ex2Column.prefWidthProperty().bind(myTable.widthProperty().multiply(0.20));

        myTable.setItems(list);
        myTable.prefWidthProperty().bind(myPane.widthProperty().multiply(0.95));
        myTable.prefHeightProperty().bind(myPane.heightProperty().multiply(0.60));
    }

    /*** 这种方式与 fxml 文件绑定使用 */
    public void editNameCommit(TableColumn.CellEditEvent<Person, String> event) {
        log.info("编辑后的值：[{}]", event.getNewValue());
        event.getTableView()
                .getItems()
                .get(event.getTablePosition().getRow())
                .setName(event.getNewValue());
    }

    public void onInitialize(ActionEvent event) {
        list.add(new Person(++index, "张三-" + index, 23 + index));
        list.add(new Person(++index, "李四-" + index, 45 + index));
    }

    public void onAdd(ActionEvent event) {
        Person person = new Person(++index, "王五-" + index, 22 + index);
        System.out.println("add: " + person);
        list.add(person);
    }

    public void onChangeStyle(ActionEvent event) {
        log.info("改样式！");
        AbstractMain.changeStyle();
    }

    public void onSelected(ActionEvent event) {
        CheckBox chk = (CheckBox) event.getSource();
        log.info("全选设置 => [{}: {}]", chk.getText(), chk.isSelected());
        list.forEach(person -> person.setSelected(chk.isSelected()));
    }

    public void onDisplay(ActionEvent event) {
        CheckBox chk = (CheckBox) event.getSource();
        log.info("隐藏设置 => [{}: {}]", chk.getText(), chk.isSelected());
        ex1Column.setVisible(chk.isSelected());
        ex2Column.setVisible(chk.isSelected());
    }

    public void onPrint(ActionEvent event) {
        log.info("遍历数据：");
        list.forEach(person -> log.info("\t [{}] [{}]", person.isSelected(), person));
    }

}
