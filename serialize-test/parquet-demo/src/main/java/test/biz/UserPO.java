package test.biz;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/26
 */
@Data
@Accessors(chain = true)
public class UserPO {

    private Integer id;
    private String name;
    private Long timestamp;
    private BigDecimal property;

    private LocalDateTime updateTime;

    private String ex1;
    private String ex2;

}
