package cn.zxf.poi.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.zxf.poi.data.CreateData;
import cn.zxf.poi.model.InvoiceDto;
import cn.zxf.poi.model.MainDto;
import cn.zxf.poi.model.OfferDto;
import cn.zxf.poi.model.OfferDto.PositionDto;
import cn.zxf.poi.model.TotalDto;
import cn.zxf.poi.model.UserDto;

public class ExportTest {

    TotalDto dto = CreateData.create();
    Workbook wb  = new XSSFWorkbook();

    public static void main( String[] args ) throws IOException {
        new ExportTest().export();
    }

    void export() throws FileNotFoundException, IOException {
        fillUsers();
        fillMain();
        try ( OutputStream os = new FileOutputStream( "output/test-output.xlsx" ) ) {
            wb.write( os );
        }
    }

    void fillUsers() {
        Sheet sheet = wb.createSheet( "总表" );
        setWidth( sheet );
        CellStyle bold = this.bold();

        {
            Row row = sheet.createRow( 0 );
            String[] headList = { "name", "money", "age" };
            for ( int i = 0; i < headList.length; i++ ) {
                Cell headCell = row.createCell( i );
                headCell.setCellStyle( bold );
                headCell.setCellValue( headList[i] );
            }
        }

        List<UserDto> users = dto.getUsers();
        for ( int i = 0; i < users.size(); i++ ) {
            int j = 0;
            UserDto user = users.get( i );
            Row row = sheet.createRow( i + 1 );
            fillCell( row, j++, user.getName() );
            fillCell( row, j++, user.getMoney() );
            fillCell( row, j++, user.getAge() );
        }
    }

    void fillMain() {
        List<MainDto> mains = dto.getMains();
        mains.forEach( mainDto -> {
            Sheet sheet = wb.createSheet( mainDto.getName() );
            setWidth( sheet );
            int rowI = 0;
            rowI = fillOffers( mainDto.getOffers(), sheet, rowI );
            rowI = fillInvoices( mainDto.getInvoices(), sheet, rowI );
        } );
    }

    int fillInvoices( List<InvoiceDto> invoices, Sheet sheet, int rowI ) {
        CellStyle bold = this.bold();
        {
            rowI++;
            Row row = sheet.createRow( rowI++ );
//            row = sheet.createRow( rowI++ );
            Cell headCell = row.createCell( 0 );
            headCell.setCellStyle( bold );
            headCell.setCellValue( "开票 " + invoices.size() + " 个" );
        }
        {
            Row row = sheet.createRow( rowI++ );
            String[] headList = { "id", "money" };
            for ( int i = 0; i < headList.length; i++ ) {
                Cell headCell = row.createCell( i );
                headCell.setCellStyle( bold );
                headCell.setCellValue( headList[i] );
            }
        }
        for ( int i = 0; i < invoices.size(); i++ ) {
            int j = 0;
            InvoiceDto invoice = invoices.get( i );
            Row row = sheet.createRow( rowI++ );
            fillCell( row, j++, invoice.getId() );
            fillCell( row, j++, invoice.getMoney() );
        }
        return rowI;
    }

    int fillOffers( List<OfferDto> offers, Sheet sheet, int rowI ) {
        CellStyle bold = this.bold();
        {
            Row row = sheet.createRow( rowI++ );
            Cell headCell = row.createCell( 0 );
            headCell.setCellStyle( bold );
            headCell.setCellValue( "Offer " + offers.size() + " 个" );
        }
        {
            Row row = sheet.createRow( rowI++ );
            String[] headList = { "id", "money" };
            for ( int i = 0; i < headList.length; i++ ) {
                Cell headCell = row.createCell( i );
                headCell.setCellStyle( bold );
                headCell.setCellValue( headList[i] );
            }
        }
        for ( int i = 0; i < offers.size(); i++ ) {
            int j = 0;
            OfferDto offer = offers.get( i );
            Row row = sheet.createRow( rowI++ );
            fillCell( row, j++, offer.getId() );
            fillCell( row, j++, offer.getMoney() );
            List<PositionDto> positions = offer.getPositions();
            int posSize = positions.size();

            CellRangeAddress cellRangeAddress = new CellRangeAddress( rowI - 1, rowI - 1 + posSize - 1, j - 1, j - 1 );
            sheet.addMergedRegion( cellRangeAddress );

            for ( int pi = 0; pi < posSize; pi++ ) {
                PositionDto posDto = positions.get( pi );
                int ji = j;
                if ( pi > 0 )
                    row = sheet.createRow( rowI++ );
                fillCell( row, ji++, posDto.getId() );
                fillCell( row, ji++, posDto.getUserName() );
            }
        }
        return rowI;
    }

    void setWidth( Sheet sheet ) {
        IntStream.rangeClosed( 0, 10 )
                .forEach( i -> {
                    sheet.setColumnWidth( i, 20 * 256 );
                } );
    }

    void fillCell( Row row, int index, Object obj ) {
        String value = obj == null ? "" : obj.toString();
        CellStyle nonBold = this.nonBold();
        Cell cell = row.createCell( index );
        cell.setCellStyle( nonBold );
        cell.setCellValue( value );
    }

    CellStyle bold() {
        return this.getCellStyle( true );
    }

    CellStyle nonBold() {
        return this.getCellStyle( false );
    }

    CellStyle getCellStyle( boolean bold ) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( (short) 12 );// 设置字体大小
        font.setBold( bold ); // 加粗
        style.setAlignment( HorizontalAlignment.CENTER );// 让单元格居中
        style.setVerticalAlignment( VerticalAlignment.CENTER );
        style.setFont( font );
        return style;
    }

}
