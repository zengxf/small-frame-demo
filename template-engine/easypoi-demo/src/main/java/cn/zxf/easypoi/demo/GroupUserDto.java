package cn.zxf.easypoi.demo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GroupUserDto {

    private String id;

    private String name;

    private String userName;

    private Integer age;

    private Date createDate;

}
