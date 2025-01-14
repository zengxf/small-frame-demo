package test;

import org.junit.Test;
import test.vo.UserPO;
import test.vo.UserReq;
import test.vo.UserRes;

public class UserMapperTest {

    UserMapper mapper = UserMapper.INSTANCE;


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

        UserPO po = mapper.reqToPo(req);
        System.out.println("po: " + po);

        UserRes res = mapper.poToRes(po);
        System.out.println("res: " + res);

        UserRes res2 = mapper.poToRes2(po);
        System.out.println("res2: " + res2);

        UserRes res3 = mapper.poToRes3(po);
        System.out.println("res3: " + res3);
    }

}