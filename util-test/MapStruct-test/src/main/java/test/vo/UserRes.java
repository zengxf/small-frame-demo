package test.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
@Data
@Accessors(chain = true)
public class UserRes {

    /**
     * ID
     */
    private Long userId;        // ID
    /**
     * 姓名
     */
    private String name;


    /**
     * 归属主体名称
     */
    private String affiliationName;


    /**
     * 业务跟进人 ID
     */
    private Long bizFollowerId;
    /**
     * 业务跟进人姓名
     */
    private String bizFollowerName;


    /**
     * 状态
     */
    private String status;      // userStatus

}
