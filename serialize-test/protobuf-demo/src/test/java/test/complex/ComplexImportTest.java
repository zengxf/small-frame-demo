package test.complex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.simple.SimpleEnum;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/21.
 */
@Slf4j
public class ComplexImportTest {

    @Test
    public void test() {
        ComplexImport.Complex.Builder builder = ComplexImport.Complex.newBuilder();
        builder.setId(111)
                .setContent("中-22222")
                .setType(SimpleEnum.Type.LOGIN_REQUEST);
        ComplexImport.Complex complex = builder.build();
        log.info("complex: [{}]", complex);
    }

}