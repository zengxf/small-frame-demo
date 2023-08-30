package test.url.read_html.rule;

public class HrefRule {

    public final String listCssQuery;
    public final String itemCssQuery;
    public final String attrKey;

    /**
     * <pre>
     * this.itemCssQuery = "a";
     * this.attrKey = "href";
     * </pre>
     * 
     * @param cssQuery
     */
    public HrefRule(String cssQuery) {
	super();
	this.itemCssQuery = "a";
	this.attrKey = "href";
	this.listCssQuery = cssQuery;
    }

    public HrefRule(String listCssQuery, String itemCssQuery, String attrKey) {
	super();
	this.listCssQuery = listCssQuery;
	this.itemCssQuery = itemCssQuery;
	this.attrKey = attrKey;
    }

}
