package cn.zxf.easyexcel.demo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;

public class TestCreateGroupUserExcel {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String fileName = "D:\\Data\\test\\excel-test\\user-group.xlsx";

        ExcelWriter writer = EasyExcelFactory.write()
                .file(fileName)
                .head(GroupUserDto.class)
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(2, 3, 0, 0))
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(2, 3, 1, 1))
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(4, 6, 0, 0))
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(4, 6, 1, 1))
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(7, 10, 0, 0))
                .registerWriteHandler(new OnceAbsoluteMergeStrategy(7, 10, 1, 1))
                .build();
        writer.write(data(), EasyExcelFactory.writerSheet()
                .sheetName("用户组-数据")
                .build());

        writer.finish();

        System.out.println("OK! 用时： " + (System.currentTimeMillis() - start) + " ms");
    }

    static List<GroupUserDto> data() {
        return groups().stream()
                .flatMap(group -> group.getUsers()
                        .stream()
                        .map(user -> GroupUserDto.builder()
                                .id(group.getId())
                                .name(group.getName())
                                .userName(user.getName())
                                .age(user.getAge())
                                .createDate(user.getCreateDate())
                                .build()))
                .collect(Collectors.toList());
    }

    static List<GroupDto> groups() {
        return IntStream.range(1, 5)
                .boxed()
                .map(i -> GroupDto.builder()
                        .id("Group=Id=" + i)
                        .name("Group=Name=" + i)
                        .users(users(i))
                        .build())
                .collect(Collectors.toList());
    }

    static List<UserDto> users(int sign) {
        return IntStream.rangeClosed(1, sign)
                .boxed()
                .map(i -> UserDto.builder()
                        .name(sign + "=User=" + i)
                        .age(22 + i)
                        .createDate(new Date(System.currentTimeMillis() + i * 24L * 3600 * 1000))
                        .build())
                .collect(Collectors.toList());
    }

}
