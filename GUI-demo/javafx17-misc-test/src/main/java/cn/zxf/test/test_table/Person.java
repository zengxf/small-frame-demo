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

    Integer id;
    String name;
    Integer age;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    public Person(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
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
