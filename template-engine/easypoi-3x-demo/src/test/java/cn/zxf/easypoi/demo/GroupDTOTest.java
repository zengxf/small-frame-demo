package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
public class GroupDTOTest {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Data\\test\\excel-test\\ExcelExportFor-Group.xlsx";

        List<GroupDto> list = getData();

        ExportParams params = new ExportParams("test-0000-8888", "测试");
        Workbook workbook = ExcelExportUtil.exportExcel(params, GroupDto.class, list);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

    static List<GroupDto> getData() {
        List<GroupDto> list = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> {
            List<UserDto> users = IntStream.range(1, (i % 3) + 1) // 记录条数为：0 1 2，所以会有空的
                    .mapToObj(subI -> new UserDto()
                            .setName(String.format("测试 - %d - %d", i, subI))
                            .setAge(100 + i)
                            .setCreateDate(new Date())
                            .setCreateDate2(LocalDate.now().plusDays(i))
                    )
                    .collect(Collectors.toList());

            GroupDto dto = new GroupDto()
                    .setId("X-" + i)
                    .setName("中-Test-" + i)
                    .setUsers(users);

            list.add(dto);
        });
        return list;
    }

}
