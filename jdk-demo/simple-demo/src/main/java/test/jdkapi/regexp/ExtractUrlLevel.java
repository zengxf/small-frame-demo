package test.jdkapi.regexp;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractUrlLevel {

    public static void main(String[] args) {

	// String url = "http://www.ss.com/AAA/ddd/2486.html";
	String url = "http://www.ss.com/AAA/ddd/2486_3.html";
	// String url = "http://www.ss.com/AAA/ddd/2486_23.jsp";

	int[] arr = extractLv1Lv2(url);
	System.out.println(Arrays.toString(arr));

    }

    /**
     * 提取URL里面Lv1, Lv2的值
     * 
     * @param url
     * @return
     */
    public static int[] extractLv1Lv2(String url) {
	int[] arr = { 0, 0 };

	String regex = "(\\d+)\\_?(\\d+)?\\.\\w+";
	Pattern p = Pattern.compile(regex);
	Matcher m = p.matcher(url);

	if (m.find()) {
	    String lv1 = m.group(1);
	    String lv2 = m.group(2);
	    if (lv1 != null)
		arr[0] = Integer.parseInt(lv1);
	    if (lv2 != null)
		arr[1] = Integer.parseInt(lv2);
	}

	return arr;
    }

}
