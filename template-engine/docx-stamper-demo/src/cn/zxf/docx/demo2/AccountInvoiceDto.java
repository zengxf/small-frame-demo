package cn.zxf.docx.demo2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInvoiceDto {

    private String   invoiceId;     // 发票Id
    private String   invoiceType;   // 发票类型
    private String   companyName;   // 客户名称
    private String   posName;       // 职位名称
    private String   cddName;       // 候选人名称
    private String   entryTime;     // 时间
    private String   uppercaseMoney;// 大写金额

    private BillInfo billInfo;      // 账单详情

    @Data
    @Builder
    public static class BillInfo {
        private String invoiceTitle;               // 发票抬头
        private String invoiceMoney;               // 发票金额
        private String invoiceContent;             // 发票内容
        private String taxpayerId;                 // 纳税人识别号
        private String bankAccount;                // 客户开户行
        private String bankCard;                   // 银行账号（对公）
        private String customerAddress;            // 客户地址
        private String customerPhone;              // 客户电话
        private String invoiceMailingAddress;      // 发票邮寄地址
        private String invoiceRecipient;           // 发票收件人
        private String recipientContactInformation;// 收件人联系方式
        private String applier;                    // 顾问
        private String phone;                      // 电话
    }

}
