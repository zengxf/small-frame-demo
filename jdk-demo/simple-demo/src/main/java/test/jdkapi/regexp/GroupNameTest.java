package test.jdkapi.regexp;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * 正则组名测试
 * <br/>
 * Created by ZXFeng on 2022/9/13.
 */
@Slf4j
public class GroupNameTest {

    public static void main(String[] args) {
        String delSql = "Delete   FROM  User Where id = 3 ";
        String regex = "\\s*(delete)\\s+(from)\\s+(?<table>\\w+)\\s+(where)(?<where>[\\s\\S]+)";
        Matcher delMatcher = Pattern.compile(regex, CASE_INSENSITIVE)
                .matcher(delSql);
        delMatcher.find();
        String table = delMatcher.group("table");
        String where = delMatcher.group("where");
        log.info("table: [{}], where: [{}]", table, where);
    }

}
