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
                SELECT
                    asset_name AS assetName,            -- 资产名称
                    basic_brand_name AS basicBrandName, -- 品牌
                    basic_brand_model_name AS basicBrandModelName, -- 型号
                    asset_number AS assetNumber,        -- 资产编码
                    maintain_time AS maintainTime,      -- 维修时间
                    maintain_number AS maintainNumber,  -- 维修单号
                    maintain_amount AS maintainAmount,  -- 维修金额
                    maintain_reason AS maintainReason,  -- 维修原因
                    apply_person_name AS applyPersonName,   -- 维修申请人
                    apply_person_duty AS applyPersonDuty,   -- 申请人岗位
                    apply_department AS applyDepartment,    -- 申请人部门
                    asset_name AS assetName, -- xxx
                FROM ads_ims_asset_maintain_detail
                WHERE 1 = 1 \s
                    AND #'$genericQuery$'#
                    AND ${bdDataScope}
                    <% if (!isEmpty(levelNum)) { %>  AND lv${levelNum}_id = ${departmentId} <% } %>     \s
                    <% if (isContain != 1) { %>  AND lv${levelNum + 1}_id != 0 <% } %>  \s
                ;
                """;

        // 获取模板
        Template t = gt.getTemplate(fmt);
        t.binding("bdDataScope", "1 = 1");
        t.binding("levelNum", 1);
        t.binding("isContain", 2);
        t.binding("departmentId", 60);

        // 渲染结果
        String str = t.render();
        System.out.println(str);
        log.info("sql: \n\n{}\n", str);
    }

}
