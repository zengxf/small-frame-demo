package cn.zxf.poi.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDto {

    private String     id;
    private BigDecimal money;
    private List<PositionDto> positions;

    @Data
    @Builder
    public static class PositionDto {
        private String id;
        private String userName;
    }

}
