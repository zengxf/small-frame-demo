package cn.zxf.kryo.demo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Long         id;
    private String       name;
    private BigDecimal   money;
    private Double       doubleV;
    private Short        shortV;
    private Float        floatV;
    private Date         createDate;
    private List<String> tags;

}
