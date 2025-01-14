package test.vo;

import lombok.Data;

import java.util.List;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
@Data
public class GroupBO {

    private Long id;

    private String name;

    private UserPO admin;

    private List<UserPO> users;

}
