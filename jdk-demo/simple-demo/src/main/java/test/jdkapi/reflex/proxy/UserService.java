package test.jdkapi.reflex.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * <br>
 * Created by ZXFeng on 2023/7/24
 */
@Slf4j
public class UserService implements IService{

    @Override
    public void say() {
        log.info("--------- User 666 --------");
    }

}
