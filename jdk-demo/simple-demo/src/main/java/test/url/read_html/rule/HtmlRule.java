package test.url.read_html.rule;

import java.util.ArrayList;
import java.util.List;

public class HtmlRule {

    public TextRule labelRule;
    public TextRule titleRule;
    public ImageRule imageRule;
    public List<HrefRule> hrefRules = new ArrayList<>();// 有些页面会有多种样式

}
