package cn.zxf.easypoi.demo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDto {

    private String name;

    private Integer age;

    private Date createDate;

}
