package cn.zxf.test.test_table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Created by zengxf on 2019/8/28.
 */
@Data
public class Person {

    final BooleanProperty selected = new SimpleBooleanProperty(false);
    Integer id;
    String name;
    Integer age;
    String ex1;
    String ex2;

    public Person(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ex1 = "扩展1: " + id;
        this.ex2 = "扩展2-XX: " + id;
    }


    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

}
