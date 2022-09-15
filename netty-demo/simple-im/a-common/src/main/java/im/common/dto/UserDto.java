package im.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <br/>
 * Created by ZXFeng on  2021/11/15.
 */
@Data
@AllArgsConstructor(staticName = "of")
public class UserDto {

    private String name;
    private Integer age;

}
