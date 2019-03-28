package cn.zxf.jxls.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String  name;
    private Integer age;

}
