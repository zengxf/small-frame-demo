package cn.zxf.poi.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceDto {

    private String     id;
    private BigDecimal money;

}
