package test;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors( chain = true )
@XStreamAlias( "用户" ) // 重命名注解
public class UserAnnotation {
    @XStreamAlias( "姓名" )
    private String       name;
    private String       pwd;
    @XStreamConverter( value = BooleanConverter.class, booleans = { false }, strings = { "男", "女" } ) // 设置转换器
    private boolean      sex;
    @XStreamAlias( "年龄" )
    private int          age;
    @XStreamOmitField // 隐藏字段
    private String       address;
    @XStreamAsAttribute // 把字段节点设置成属性
    private String       note;
    @XStreamImplicit // 省略集合节点
    private List<String> tags;
}
