package common_model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Data
@AllArgsConstructor
public class User {

    private String name;
    private Integer age;

    public static User of(String name, Integer age) {
        return new User(name, age);
    }

}
