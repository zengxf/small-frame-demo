package test;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import test.vo.GroupBO;
import test.vo.GroupReq;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
@Mapper(uses = UserMapper.class) // List 要单独一个映射
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);


    // @Mapping(source = "admin.userId", target = "admin.id")
    // @Mapping(source = "admin.status", target = "admin.userStatus")
    GroupBO reqToBo(GroupReq req);

}
