package cn.zxf.test;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestObject {

    public static void main(String[] args) throws IOException {
        UserInfo user = UserInfo.builder()
                .name("zxf")
                .age(22)
                .sex("man")
                .build();

        XmlMapper mapper = new XmlMapper();

        String xml = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(user);
        System.out.println(xml);

        File file = new File("D:\\Data\\xml\\user.txt");
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, user);

        UserInfo user2 = mapper.readValue(xml, UserInfo.class);
        System.out.println(user2);

        UserInfo user3 = mapper.readValue(file, UserInfo.class);
        System.out.println(user3);
    }

}
