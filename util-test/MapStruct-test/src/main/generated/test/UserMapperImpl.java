package test;

import javax.annotation.processing.Generated;
import test.vo.UserPO;
import test.vo.UserReq;
import test.vo.UserRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-14T14:31:25+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Eclipse Adoptium)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserPO reqToPo(UserReq req) {
        if ( req == null ) {
            return null;
        }

        UserPO userPO = new UserPO();

        userPO.setId( req.getUserId() );
        userPO.setUserStatus( req.getStatus() );
        userPO.setName( req.getName() );
        userPO.setAffiliationName( req.getAffiliationName() );
        userPO.setBizFollowerId( req.getBizFollowerId() );
        userPO.setBizFollowerName( req.getBizFollowerName() );

        return userPO;
    }

    @Override
    public UserRes poToRes(UserPO po) {
        if ( po == null ) {
            return null;
        }

        UserRes userRes = new UserRes();

        userRes.setUserId( po.getId() );
        userRes.setStatus( po.getUserStatus() );
        userRes.setName( po.getName() );
        userRes.setAffiliationName( po.getAffiliationName() );
        userRes.setBizFollowerId( po.getBizFollowerId() );
        userRes.setBizFollowerName( po.getBizFollowerName() );

        return userRes;
    }

    @Override
    public UserRes poToRes2(UserPO po) {
        if ( po == null ) {
            return null;
        }

        UserRes userRes = new UserRes();

        userRes.setUserId( po.getId() );
        userRes.setStatus( po.getUserStatus() );
        userRes.setName( po.getName() );
        userRes.setAffiliationName( po.getAffiliationName() );
        userRes.setBizFollowerId( po.getBizFollowerId() );
        userRes.setBizFollowerName( po.getBizFollowerName() );

        return userRes;
    }

    @Override
    public UserRes poToRes3(UserPO po) {
        if ( po == null ) {
            return null;
        }

        UserRes userRes = new UserRes();

        userRes.setName( po.getName() );
        userRes.setAffiliationName( po.getAffiliationName() );
        userRes.setBizFollowerId( po.getBizFollowerId() );
        userRes.setBizFollowerName( po.getBizFollowerName() );

        return userRes;
    }
}
