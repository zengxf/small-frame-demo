package test;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

@Slf4j
public class TestSQL1 {

    public static void main(String[] args) throws IOException {
        // 初始化代码
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        String fmt = """
                -- 维修明细页
                 SELECT
                     md.asset_name AS assetName,            -- 资产名称
                     md.basic_brand_name AS basicBrandName, -- 品牌
                     md.basic_brand_model_name AS basicBrandModelName, -- 型号
                     md.asset_number AS assetNumber,        -- 资产编码
                     md.maintain_time AS maintainTime,      -- 维修时间
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
                 FROM ads_ims_asset_maintain_detail AS md
                     LEFT JOIN (
                         SELECT\s
                             business_type, business_str_id, MAX(id) newest_id, COUNT(*) num\s
                         FROM bi_report_common_followup
                         WHERE business_type = '10'
                         GROUP BY business_type, business_str_id
                     ) AS cfs    -- s 统计
                         ON cfs.business_str_id = md.maintain_number
                     LEFT JOIN bi_report_common_followup AS cfd  -- d 详情
                         ON cfd.id = cfs.newest_id
                 WHERE 1 = 1
                     AND #'$genericQuery$'#      -- 通用查询以 md. 开头
                     AND ${bdDataScope}
                     <% if (!isEmpty(levelNum)) { %>  AND md.lv${levelNum}_id = ${departmentId} <% } %>
                     <% if (isContain != 1) { %>  AND md.lv${levelNum + 1}_id != 0 <% } %>
                 <% if (!isEmpty(orderByClauses)) { println(' ORDER BY ' + orderByClauses + ''); } %>
                 <% if (isEmpty(orderByClauses)) { println(' ORDER BY departmentId DESC '); } %>
                 <% if (isNotEmpty(export) && export == "1") {\s
                     %>  LIMIT ${(_page - 1) * _pageSize}, ${_pageSize} <%\s
                 } %>
                """;

        // 获取模板
        Template t = gt.getTemplate(fmt);
        t.binding("bdDataScope", "1 = 1");
        t.binding("levelNum", 1);
        t.binding("isContain", 2);
        t.binding("departmentId", 60);
        t.binding("export", "1");
        t.binding("_page", 1);
        t.binding("_pageSize", 200);

        // 渲染结果
        String str = t.render();
        System.out.println(str);
        log.info("sql: \n\n{}\n", str);
    }

}
