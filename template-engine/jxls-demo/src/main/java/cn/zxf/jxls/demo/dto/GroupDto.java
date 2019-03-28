package cn.zxf.jxls.demo.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {

    private String	  id;
    private String	  name;
    private List<UserDto> users;

}
