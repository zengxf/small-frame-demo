package cn.zxf.poi.model;

import java.util.List;

import lombok.Data;

@Data
public class TotalDto {

    private List<UserDto> users;
    private List<MainDto> mains;

}
