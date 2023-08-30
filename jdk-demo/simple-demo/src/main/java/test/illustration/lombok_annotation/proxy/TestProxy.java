package test.illustration.lombok_annotation.proxy;

import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

/**
 * 可反编译查看
 * <p>
 * Created by zengxf on 2019-07-02
 */
// M:\project\zxf_super_demo\simple-demo\bin\main\test\illustration\lombok_annotation\proxy
@NoArgsConstructor( staticName = "of" )
public class TestProxy implements ITest {

    @Delegate
    private ITest test = TestImpl.of( "zxf" );

}
