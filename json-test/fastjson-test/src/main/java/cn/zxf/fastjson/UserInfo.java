package cn.zxf.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    
    @JSONField( name = "user-name" )
    private String  name;
    private Integer age;
    private String  sex;

}
