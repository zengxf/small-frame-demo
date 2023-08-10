package cn.zxf.easypoi.demo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupDto {

    private String id;

    private String name;

    private List<UserDto> users;

}
