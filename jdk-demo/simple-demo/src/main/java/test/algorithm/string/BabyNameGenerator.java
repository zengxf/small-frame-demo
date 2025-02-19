package test.algorithm.string;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 小孩起名
 */
public class BabyNameGenerator {
    private static final int MAX_COUNT = 20;
    private static final Map<String, String[]> FIVE_ELEMENTS; // 五行对应的候选汉字

    // 初始化五行对应的汉字列表
    static {
        FIVE_ELEMENTS = new HashMap<>();
        FIVE_ELEMENTS.put("金", new String[]{"白", "银", "刚", "铭", "锋", "锦", "宝", "锐", "钊", "钢"});
        FIVE_ELEMENTS.put("木", new String[]{"松", "柏", "桦", "橙", "权", "彬", "栋", "林", "桦", "材"});
        FIVE_ELEMENTS.put("水", new String[]{"波", "涛", "海", "润", "泽", "洋", "泉", "清", "澜", "涵"});
        FIVE_ELEMENTS.put("火", new String[]{"炎", "昊", "灿", "煜", "炽", "照", "辉", "昕", "炫", "炬"});
        FIVE_ELEMENTS.put("土", new String[]{"坤", "峰", "岩", "磊", "域", "垚", "坤", "宇", "岳", "垣"});
    }

    /**
     * 根据五行相生规则生成名字
     *
     * @param childZodiac   宝宝属相
     * @param parentZodiacs 父母属相
     * @return 生成的名字候选列表
     */
    public static String[] generateNames(String childZodiac, String[] parentZodiacs) {
        String childElement = zodiacToFiveElement(childZodiac);
        String parentsElement = combineParentsElements(parentZodiacs);

        // 根据五行相生选择合适的字
        String[] selectedCharacters = selectCharacters(childElement, parentsElement);

        // 生成双字名字
        return generateTwoCharNames(selectedCharacters);
    }

    /**
     * 根据属相获取五行属性
     *
     * @param zodiac 属相
     * @return 五行属性
     */
    private static String zodiacToFiveElement(String zodiac) {
        Map<String, String> zodiacToElement = new HashMap<>();
        zodiacToElement.put("鼠", "水");
        zodiacToElement.put("牛", "土");
        zodiacToElement.put("虎", "木");
        zodiacToElement.put("兔", "木");
        zodiacToElement.put("龙", "土");
        zodiacToElement.put("蛇", "火");
        zodiacToElement.put("马", "火");
        zodiacToElement.put("羊", "土");
        zodiacToElement.put("猴", "金");
        zodiacToElement.put("鸡", "金");
        zodiacToElement.put("狗", "土");
        zodiacToElement.put("猪", "水");
        return zodiacToElement.get(zodiac);
    }

    /**
     * 合并父母的属相五行
     *
     * @param parentZodiacs 父母属相
     * @return 合并后的五行属性
     */
    private static String combineParentsElements(String[] parentZodiacs) {
        String parent1Element = zodiacToFiveElement(parentZodiacs[0]);
        String parent2Element = zodiacToFiveElement(parentZodiacs[1]);
        return parent1Element + parent2Element;
    }

    /**
     * 根据五行选择合适的候选字
     *
     * @param childElement   孩子五行
     * @param parentsElement 父母五行
     * @return 选中的字符数组
     */
    private static String[] selectCharacters(String childElement, String parentsElement) {
        // 简单逻辑：选择与出生五行相生的字，并包含孩子和父母的五行
        Map<String, String[]> elements = new HashMap<>();
        elements.put(childElement, FIVE_ELEMENTS.get(childElement));
        elements.put(parentsElement.charAt(0) + "", FIVE_ELEMENTS.get(parentsElement.charAt(0) + ""));
        elements.put(parentsElement.charAt(1) + "", FIVE_ELEMENTS.get(parentsElement.charAt(1) + ""));

        // 合并所有候选字
        int total = 0;
        for (String[] arr : elements.values()) {
            total += arr.length;
        }
        String[] selected = new String[total];
        int index = 0;
        for (String[] arr : elements.values()) {
            for (String s : arr) {
                selected[index++] = s;
            }
        }
        return selected;
    }

    /**
     * 生成双字名字
     *
     * @param characters 候选字
     * @return 双字名字数组
     */
    private static String[] generateTwoCharNames(String[] characters) {
        Random random = new Random();
        int count = Math.min(MAX_COUNT, characters.length * characters.length); // 控制生成数量
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            String first = characters[random.nextInt(characters.length)];
            String second = characters[random.nextInt(characters.length)];
            names[i] = first + second;
        }
        return names;
    }

    public static void main(String[] args) {
        // 示例输入
        String childZodiac = "龙"; // 宝宝属相
        String[] parentZodiacs = new String[]{"鼠", "牛"}; // 父母属相

        String[] names = generateNames(childZodiac, parentZodiacs);
        System.out.println("生成的双字名字如下：");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("共生成 " + names.length + " 个名字");
    }
}
