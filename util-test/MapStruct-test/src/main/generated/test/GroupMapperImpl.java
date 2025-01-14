package test;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import test.vo.GroupBO;
import test.vo.GroupReq;
import test.vo.UserPO;
import test.vo.UserReq;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-14T20:35:20+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Eclipse Adoptium)"
)
public class GroupMapperImpl implements GroupMapper {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public GroupBO reqToBo(GroupReq req) {
        if ( req == null ) {
            return null;
        }

        GroupBO groupBO = new GroupBO();

        groupBO.setId( req.getId() );
        groupBO.setName( req.getName() );
        groupBO.setAdmin( userMapper.reqToPo( req.getAdmin() ) );
        groupBO.setUsers( userReqListToUserPOList( req.getUsers() ) );

        return groupBO;
    }

    protected List<UserPO> userReqListToUserPOList(List<UserReq> list) {
        if ( list == null ) {
            return null;
        }

        List<UserPO> list1 = new ArrayList<UserPO>( list.size() );
        for ( UserReq userReq : list ) {
            list1.add( userMapper.reqToPo( userReq ) );
        }

        return list1;
    }
}
