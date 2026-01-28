package test.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;
    private int age;
    private String address;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
