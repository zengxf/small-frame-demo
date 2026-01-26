package test.biz;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/26
 */
public class DataUtils {

    public static List<UserPO> mockUsers(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    long timestamp = System.currentTimeMillis() / 1000 * 1000 + (i * 1000L);
                    BigDecimal property = BigDecimal.valueOf(866_0000L + i * 10_0000L);
                    return new UserPO()
                            .setId(800 + i)
                            .setName("Feng-" + i)
                            .setTimestamp(timestamp)
                            .setProperty(property)
                            .setUpdateTime(LocalDateTime.now().minusDays(i).withNano(0))
                            .setEx1(i % 2 == 0 ? ("ex1-v = " + i) : null)
                            .setEx2(i % 2 == 1 ? ("ex2-v = " + i) : null);
                })
                .collect(Collectors.toList());
    }

}
