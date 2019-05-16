package cn.zxf.jol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo extends SuperUserVo {
    private String  name;
    private Integer age;
    private byte    sign;
    private int     status;
}
