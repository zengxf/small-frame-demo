package cn.plugin;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.RegexReplacement;

import java.nio.charset.Charset;

/**
 * <br/>
 * Created by ZXFeng on 2022/6/14.
 */
@Plugin(name = "MyPatternLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class MyPatternLayout extends AbstractStringLayout {

    private PatternLayout patternLayout;
    private Boolean sensitive;
    private RegexReplacement[] replaces;


    protected MyPatternLayout(
            Charset charset, String pattern, Boolean sensitive, RegexReplacement[] replaces
    ) {
        super(charset);
        patternLayout = PatternLayout.newBuilder().withPattern(pattern).build();
        this.sensitive = sensitive;
        this.replaces = replaces;
    }


    /*** 插件构造工厂方法 */
    @PluginFactory
    public static Layout<String> createLayout(
            @PluginAttribute(value = "pattern") final String pattern,       // 输出pattern
            @PluginAttribute(value = "charset", defaultString = "UTF-8") final Charset charset, // 字符集
            @PluginAttribute(value = "sensitive") final Boolean sensitive,  // 是否开启脱敏
            @PluginElement("replace") final RegexReplacement[] replaces     // 脱敏规则
    ) {
        return new MyPatternLayout(charset, pattern, sensitive, replaces);
    }


    @Override
    public String toSerializable(LogEvent event) {
        // 原日志信息
        String msg = this.patternLayout.toSerializable(event);
        if (Boolean.FALSE.equals(this.sensitive)) {
            return msg;     // 不脱敏，直接返回
        }
        if (this.replaces == null || this.replaces.length == 0) {
            throw new RuntimeException("未配置脱敏规则，请检查配置重试");
        }
        for (RegexReplacement replace : this.replaces) {
            msg = replace.format(msg); // 遍历脱敏正则 & 替换敏感数据
        }
        return msg;         // 脱敏后的日志
    }

}
