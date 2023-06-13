package test;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

/**
 * 安全输出：使用 `maintainNumType! == 5` 写法就不用判断 `maintainNumType` 是否为空
 * <br>
 * Created by ZXFeng on 2023/6/13
 */
@Slf4j
public class TestSecureSQL {

    public static void main(String[] args) throws IOException {
        // 初始化代码
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        String fmt = """
                  -- 维修明细页 bigdata.ims.report.maintainDetail.search
                  SELECT
                      md.query_month AS queryMonth,          -- 月份
                      md.asset_id AS assetId,                -- 资产ID
                      md.asset_name AS assetName,            -- 资产名称
                      md.basic_brand_name AS basicBrandName, -- 品牌
                      md.basic_brand_model_name AS basicBrandModelName, -- 型号
                      md.asset_number AS assetNumber,        -- 资产编码
                      md.maintain_time AS maintainTime,      -- 维修时间
                      md.maintain_id AS maintainId,          -- 维修ID
                      md.maintain_number AS maintainNumber,  -- 维修单号
                      md.maintain_amount AS maintainAmount,  -- 维修金额
                      md.maintain_reason AS maintainReason,  -- 维修原因
                      md.apply_person_name AS applyPersonName,   -- 维修申请人
                      md.apply_person_duty AS applyPersonDuty,   -- 申请人岗位
                      md.apply_department AS applyDepartment,    -- 申请人部门
                      cfd.remark AS followupRemark,           -- 跟进内容
                      cfd.followup_date AS followupDate,      -- 跟进时间
                      cfd.followup_name AS followupName,      -- 跟进人名称
                      cfs.num AS followupNum,                 -- 跟进记录数
                      1 AS xxx
                  FROM ads_ims.ads_ims_asset_maintain_detail AS md
                      LEFT JOIN ( -- 只为统计与查找最新记录
                          SELECT\s
                              business_type, business_num_id, MAX(id) newest_id, COUNT(*) num\s
                          FROM ims.bi_report_common_followup
                          WHERE business_type = '30'
                          GROUP BY business_type, business_num_id
                      ) AS cfs    -- s 统计
                          ON cfs.business_num_id = md.maintain_id
                      LEFT JOIN ims.bi_report_common_followup AS cfd  -- d 详情
                          ON cfd.id = cfs.newest_id -- 根据最新 ID 查找最新跟进记录
                      LEFT JOIN (
                          SELECT\s
                              asset_id, COUNT(*) maintain_num, SUBSTR(query_month, 1, 4) maintain_year
                          FROM ads_ims.ads_ims_asset_maintain_detail
                          GROUP BY asset_id, maintain_year
                      ) AS mtn -- 年维修次数表
                          ON mtn.asset_id = md.asset_id AND mtn.maintain_year = SUBSTR('${queryMonth!}', 1, 4)
                  WHERE 1 = 1
                      AND #'$genericQuery$'#      -- 通用查询以 'md.' 开头
                      AND ${bdDataScope}
                      <% if (!isEmpty(levelNum)) { %>\s
                          AND md.lv${levelNum}_id = ${departmentId}\s
                      <% } %>
                      <% if (!isEmpty(queryMonth)) { %>\s
                          AND md.query_month = '${queryMonth}' -- 查询月份设置
                      <% } %>
                      <% if (maintainNumType! == 1) { %>\s
                          AND mtn.maintain_num >= 6 AND mtn.maintain_num < 12
                      <% } %>
                      <% if (maintainNumType! == 2) { %>\s
                          AND mtn.maintain_num >= 12 AND mtn.maintain_num < 18
                      <% } %>
                      <% if (maintainNumType! == 3) { %>\s
                          AND mtn.maintain_num >= 18 AND mtn.maintain_num < 24
                      <% } %>
                      <% if (maintainNumType! == 4) { %>\s
                          AND mtn.maintain_num >= 24 AND mtn.maintain_num < 32
                      <% } %>
                      <% if (maintainNumType! == 5) { %>\s
                          AND mtn.maintain_num >= 32
                      <% } %>
                  <% if (!isEmpty(orderByClauses)) { println(' ORDER BY ' + orderByClauses + ''); } %>
                  <% if (isEmpty(orderByClauses)) { println(' ORDER BY maintainTime DESC'); } %>
                  <% if (isNotEmpty(export) && export == "1") { %>\s
                  LIMIT ${(_page - 1) * _pageSize}, ${_pageSize}\s
                  <% } %>
                """;

        // 获取模板
        Template t = gt.getTemplate(fmt);
        t.binding("bdDataScope", "1 = 1");
        t.binding("levelNum", 4);
        t.binding("departmentId", 73);
        t.binding("maintainNumType", 1);
        t.binding("export", "1");
        t.binding("orderByClauses", "md.id");
        t.binding("_page", 1);
        t.binding("_pageSize", 200);

        // 渲染结果
        String str = t.render();
        System.out.println(str);
        log.info("sql: \n\n{}\n", str);
    }

}
