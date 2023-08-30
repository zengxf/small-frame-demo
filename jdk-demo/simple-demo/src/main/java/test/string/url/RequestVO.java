package test.string.url;

import java.util.HashMap;
import java.util.Map;

public class RequestVO {

    private String		      mainUrl;
    private final Map<String, String> param = new HashMap<>();

    public String getMainUrl() {
	return mainUrl;
    }

    public void setMainUrl( String mainUrl ) {
	this.mainUrl = mainUrl;
    }

    public Map<String, String> getParam() {
	return param;
    }

}
