package jexl3;

import java.util.HashMap;
import java.util.Map;

public class TestBoolean extends Jexl3Utils {

    public static void main(String... args) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("money", 6100);
            String expression = "money >= 2000 && money <= 4000";
            Object code = execute(expression, map);
            System.out.println((Boolean) code);
            map.put("money", 2520);
            code = execute(expression, map);
            System.out.println((Boolean) code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
