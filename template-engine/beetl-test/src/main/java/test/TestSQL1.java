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
                -- 物料满意度明细页-查询 bigdata.ims.report.evaluationDetail.productSearch
                SELECT
                    md.query_month AS queryMonth,          -- 月份
                    md.product_name AS productName,        -- 物料名称
                    md.basic_brand_name AS basicBrandName, -- 品牌
                    md.basic_brand_model_name AS basicBrandModelName, -- 型号
                    md.product_number AS productNumber,    -- 料号
                    md.user_satisfaction as userSatisfaction,           -- 使用满意度
                    md.satisfaction_rating as satisfactionRating,       -- 满意度评级
                    md.evaluation_description as evaluationDescription, -- 评价内容
                    md.evaluation_person_name as evaluationPersonName,  -- 评价人名称
                    md.evaluation_date as evaluationDate,               -- 评级时间
                    1 AS xxx
                FROM ads_ims.ads_ims_evaluation_detail AS md
                WHERE 1 = 1
                    AND #'$genericQuery$'#      -- 通用查询以 'md.' 开头
                    AND ${bdDataScope}
                    AND asset_number IS NULL
                    <% if (!isEmpty(levelNum)) { %> \s
                        AND md.lv${levelNum}_id = ${departmentId}\s
                    <% } %>
                    <% if (isContain != 1) { %>\s
                        AND md.lv${levelNum + 1}_id != 0\s
                    <% } %>
                <% if (!isEmpty(orderByClauses)) { println(' ORDER BY ' + orderByClauses + ''); } %>
                <% if (isEmpty(orderByClauses)) { println(' ORDER BY evaluationDate DESC'); } %>
                <% if (isNotEmpty(export) && export == "1") { %> -- 相当于 MyBatis if 标签
                LIMIT ${(_page - 1) * _pageSize}, ${_pageSize}\s
                <% } %>
                
                <%
                // 注释写在内部
                /*
                 * 块注释
                 */
                %>
                """;

        // 获取模板
        Template t = gt.getTemplate(fmt);
        t.binding("bdDataScope", "1 = 1");
        t.binding("levelNum", 4);
        t.binding("isContain", 2);
        t.binding("departmentId", 73);
        t.binding("export", "1");
        t.binding("_page", 1);
        t.binding("_pageSize", 200);

        // 渲染结果
        String str = t.render();
        System.out.println(str);
        log.info("sql: \n\n{}\n", str);
    }

}
