package cn.zxf.docx.demo2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.wickedsource.docxstamper.DocxStamper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo2 {
    public static void main( String[] args ) throws IOException {

        AccountInvoiceDto.BillInfo billInfo = AccountInvoiceDto.BillInfo.builder() //
                .invoiceTitle( "test" ) // 发票抬头
                .invoiceMoney( "2000" ) // 发票金额
                .invoiceContent( "发票内容" ) // 发票内容
                .taxpayerId( "纳税人识别号" ) // 纳税人识别号
                .bankAccount( "客户开户行" ) // 客户开户行
                .bankCard( "银行账号（对公）" ) // 银行账号（对公）
                .customerAddress( "客户地址" ) // 客户地址
                .customerPhone( "客户电话" ) // 客户电话
                .invoiceMailingAddress( "发票邮寄地址" ) // 发票邮寄地址
                .invoiceRecipient( "发票收件人" ) // 发票收件人
                .recipientContactInformation( "收件人联系方式" ) // 收件人联系方式
                .applier( "顾问" ) // 顾问
                .phone( "电话" ) // 电话
                .build();
        AccountInvoiceDto context = AccountInvoiceDto.builder() //
                .invoiceId( "发票Id" ) // 发票Id
                .invoiceType( "发票类型" ) // 发票类型
                .companyName( "客户名称" ) // 客户名称
                .posName( "职位名称 " ) // 职位名称
                .cddName( "候选人名称" ) // 候选人名称
                .entryTime( "时间" ) // 时间
                .uppercaseMoney( "大写金额" ) // 大写金额
                .billInfo( billInfo ) // 账单详情
                .build();

        long start = System.currentTimeMillis();
        out( context, "bill-Info-2.12.3.docx", "C:/Users/Administrator/Desktop/docx/demo2-res-1.docx" );
        log.info( "use time: {} ms", System.currentTimeMillis() - start );

        log.info( "context: {}", context );
        log.info( "OK!!!" );
    }

    static void out( AccountInvoiceDto context, String tempName, String outFile ) throws FileNotFoundException, IOException {
        InputStream template = Demo2.class.getResourceAsStream( tempName );
        OutputStream out = new FileOutputStream( outFile );
        DocxStamper<AccountInvoiceDto> stamper = new DocxStamper<>();
        stamper.stamp( template, context, out );
        out.close();
    }

}
