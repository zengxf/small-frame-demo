package test.url.read_html;

import java.util.ArrayList;
import java.util.List;

public class HtmlBO {

    public String label;
    public String title;
    public List<String> imageUrlList = new ArrayList<>();
    public List<String> hrefUrlList = new ArrayList<>();

    @Override
    public String toString() {
	return "HtmlBO [label=" + label + ", title=" + title + ", \r\n imageUrl=" + imageUrlList + ", \r\n hrefUrlList=" + hrefUrlList + "]";
    }

}
