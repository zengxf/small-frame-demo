package test.url.read_html.rule;

public class ImageRule {

    public final String cssQuery;
    public final String attrKey;

    /**
     * <pre>
     * this.attrKey = "src";
     * </pre>
     * 
     * @param cssQuery
     */
    public ImageRule(String cssQuery) {
	super();
	this.attrKey = "src";
	this.cssQuery = cssQuery;
    }

    public ImageRule(String cssQuery, String attrKey) {
	super();
	this.cssQuery = cssQuery;
	this.attrKey = attrKey;
    }

}
