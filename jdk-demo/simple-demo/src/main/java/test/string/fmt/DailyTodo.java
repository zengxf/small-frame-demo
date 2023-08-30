package test.string.fmt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.YearMonth;

/**
 * 每日待办事项
 * <br/>
 * Created by ZXFeng on 2022/11/15.
 */
@Slf4j
public class DailyTodo {

    static YearMonth ym = YearMonth.of(2023, 03);
    static String
            filePath = "D:/MyData/note-backup/未整理/temp.md",
            todo = "1.悬吊抬腿 □ 2.看书 □ 3.深蹲 □",
            row = "\n";

    public static void main(String[] args) throws IOException {
        StringBuilder ct = new StringBuilder(); // content
        ct.append("##### ").append(ym).append(" 日程表").append(row);

        for (int i = 1; i <= ym.lengthOfMonth(); i++) {
            DayOfWeek week = ym.atDay(i).getDayOfWeek();
            if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY) {
                String fmt = "- [ ] **%02d** %s 2-%s A.复习 □"; // 双休多一些
                ct.append(String.format(fmt, i, todo, todo, todo));
            } else {
                String fmt = "- [ ] **%02d** %s";
                ct.append(String.format(fmt, i, todo));
            }
            if (i != ym.lengthOfMonth()) {
                ct.append(row);
            }
        }

        Path path = Paths.get(filePath);
        Files.writeString(path, ct.toString());

        log.info("----- OK 成功 -----");
    }

}
