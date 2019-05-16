package cn.zxf.poi.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainDto {

    private String           name;
    private List<OfferDto>   offers;
    private List<InvoiceDto> invoices;

}
