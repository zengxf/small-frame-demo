package cn.simple.test.new_features.jdk9.ju;

import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

import static java.util.stream.Collectors.toList;

/**
 * Created by zengxf on 2017/10/10.
 */
public class TestScanner {

    public static void main(String[] args) {
        String patternString = "\\b\\w+\\b";
        String input = "A test string,\n which contains a new line.";
        List<String> words = new Scanner(input)
                .findAll(patternString)
                .map(MatchResult::group)
                .collect(toList());
        System.out.println("Input: " + input);
        System.out.println("Words: " + words);

        System.out.println("-----");
        List<String> result = new Scanner("abc,def,,, ,ghi")
                .useDelimiter(",")
                .tokens()
                .collect(toList());
        System.out.println(result);
    }

}
