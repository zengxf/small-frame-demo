package test;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

@Slf4j
public class HelloWorld {

    public static void main(String[] args) throws IOException {
        // 初始化代码
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        // 获取模板
        Template t = gt.getTemplate("hello, ${name}");
        t.binding("name", "beetl");
        // 渲染结果
        String str = t.render();
        System.out.println(str);
        log.info("beetl-str: \n\n{}\n", str);
    }

}
