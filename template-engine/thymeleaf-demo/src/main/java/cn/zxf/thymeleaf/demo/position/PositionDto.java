package cn.zxf.thymeleaf.demo.position;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionDto {

    private String id;
    private String name;
    private String companyName;
    private String date;

}
