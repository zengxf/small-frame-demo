package test;

import org.junit.Test;
import test.vo.GroupBO;
import test.vo.GroupReq;
import test.vo.UserReq;

public class GroupMapperTest {

    GroupMapper mapper = GroupMapper.INSTANCE;

    @Test
    public void test() {
        UserReq userReq = new UserReq()
                .setUserId(888L)
                .setName("ZXF")
                .setStatus("YES")
                .setAffiliationName("百度")
                .setBizFollowerId(999L)
                .setBizFollowerName("老李");
        GroupReq req = new GroupReq()
                .setId(8899L)
                .setName("BaiDu")
                .setAdmin(userReq);
        System.out.println("req: " + req);

        GroupBO bo = mapper.reqToBo(req);
        System.out.println("bo: " + bo);
    }

}