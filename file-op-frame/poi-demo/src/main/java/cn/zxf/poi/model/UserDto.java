package cn.zxf.poi.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String     name;
    private BigDecimal money;
    private Integer    age;

}
