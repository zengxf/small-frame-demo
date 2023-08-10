package cn.zxf.easypoi.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
public class DynamicDTOTest {

    public static void main(String[] args) {
        List<DynamicDTO> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        DynamicDTO dto = new DynamicDTO()
                .setName("中-test-1")
                .setAge("22-1")
                .setDynamic(map);
    }

}
