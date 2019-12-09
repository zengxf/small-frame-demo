package cn.zxf.poi.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cn.zxf.poi.model.InvoiceDto;
import cn.zxf.poi.model.MainDto;
import cn.zxf.poi.model.OfferDto;
import cn.zxf.poi.model.TotalDto;
import cn.zxf.poi.model.UserDto;
import cn.zxf.poi.model.OfferDto.PositionDto;

public class CreateData {

    public static TotalDto create() {
        TotalDto dto = new TotalDto();
        dto.setUsers( users() );
        dto.setMains( mains() );
        return dto;
    }

    static List<UserDto> users() {
        return IntStream.of( 2, 4, 6, 7 )
                .boxed()
                .map( i -> UserDto.builder()
                        .name( "zxf-" + i )
                        .money( BigDecimal.valueOf( i * 100 ) )
                        .age( i )
                        .build() )
                .collect( Collectors.toList() );
    }

    static List<MainDto> mains() {
        return IntStream.of( 4, 7, 9, 22 )
                .boxed()
                .map( i -> MainDto.builder()
                        .name( "Main-" + i )
                        .offers( offers( i ) )
                        .invoices( invoices( i ) )
                        .build() )
                .collect( Collectors.toList() );
    }

    static List<OfferDto> offers( int index ) {
        return IntStream.of( 5, 3, 1 )
                .boxed()
                .map( i -> OfferDto.builder()
                        .id( "offer-" + index + "-" + i )
                        .money( BigDecimal.valueOf( index * 100 + i ) )
                        .positions( positions( i ) )
                        .build() )
                .collect( Collectors.toList() );
    }

    static List<PositionDto> positions( int index ) {
        return IntStream.of( 12, 31 )
                .boxed()
                .map( i -> PositionDto.builder()
                        .id( "position-" + index + "-" + i )
                        .userName( "zxf-" + index + "-" + i )
                        .build() )
                .collect( Collectors.toList() );
    }

    static List<InvoiceDto> invoices( int index ) {
        return IntStream.of( 53, 32, 11 )
                .boxed()
                .map( i -> InvoiceDto.builder()
                        .id( "invoice-" + index + "-" + i )
                        .money( BigDecimal.valueOf( index * 1000 + i ) )
                        .build() )
                .collect( Collectors.toList() );
    }

}
