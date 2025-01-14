package test;

import org.junit.Test;
import test.vo.UserPO;
import test.vo.UserReq;
import test.vo.UserRes;

public class UserMapperTest {

    @Test
    public void test() {
        UserReq req = new UserReq()
                .setUserId(888L)
                .setName("ZXF")
                // .setStatus("YES")
                .setAffiliationName("百度")
                .setBizFollowerId(999L)
                .setBizFollowerName("老李");
        System.out.println("req: " + req);

        UserPO po = UserMapper.INSTANCE.reqToPo(req);
        System.out.println("po: " + po);

        UserRes res = UserMapper.INSTANCE.poToRes(po);
        System.out.println("res: " + res);

        UserRes res2 = UserMapper.INSTANCE.poToRes2(po);
        System.out.println("res2: " + res2);

        UserRes res3 = UserMapper.INSTANCE.poToRes3(po);
        System.out.println("res3: " + res3);
    }

}