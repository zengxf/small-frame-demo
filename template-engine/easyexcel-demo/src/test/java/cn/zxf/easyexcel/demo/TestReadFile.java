package cn.zxf.easyexcel.demo;

import java.time.LocalDate;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestReadFile {

    public static void main( String[] args ) {
        String file = "C:\\Users\\Administrator\\Desktop\\aa\\indices\\399330.xls";
        List<Vo> list = EasyExcel.read( file, Vo.class, null )
                .sheet()
                .doReadSync();
        log.info( "{}", list.size() );
        log.info( "{}", list.get( 0 ) );
    }

    @Data
    public static class Vo {
        @ExcelProperty( value = "日期", converter = LDateConverter.class )
        public LocalDate date;
        @ExcelProperty( "开盘价" )
        public Double    open;
        @ExcelProperty( "涨跌幅" )
        public String    change;
        @ExcelProperty( "成交量(万手)" )
        public Double    volume;
        @ExcelProperty( "成交额(亿元)" )
        public Double    amount;
    }

    public static class LDateConverter implements Converter<LocalDate> {
        @Override
        public Class<LocalDate> supportJavaTypeKey() {
            return LocalDate.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        /*** 读的时候会调用 */
        @Override
        @SuppressWarnings( "rawtypes" )
        public LocalDate convertToJavaData( CellData cellData, ExcelContentProperty c, GlobalConfiguration g ) {
            return LocalDate.parse( cellData.getStringValue() );
        }

        /*** 写的时候会调用 */
        @Override
        public CellData<String> convertToExcelData( LocalDate value, ExcelContentProperty c, GlobalConfiguration g ) {
            return new CellData<>( value.toString() );
        }
    }

}
