package test.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
@Data
@Accessors(chain = true)
public class GroupReq {

    private Long id;

    private String name;

    private UserReq admin;

    private List<UserReq> users;

}
