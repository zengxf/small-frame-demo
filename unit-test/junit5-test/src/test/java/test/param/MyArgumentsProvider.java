package test.param;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * <br/>
 * Created by ZXFeng on  2021/10/12.
 */
@Slf4j
public class MyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        log.info("--------");
        return Test5MethodParam.getStaticDataList()
                .stream()
                .map(Arguments::of);
    }

}
