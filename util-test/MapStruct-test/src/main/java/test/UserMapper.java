package test;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import test.vo.UserPO;
import test.vo.UserReq;
import test.vo.UserRes;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
@Mapper
// @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
// @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "userId", target = "id")
    @Mapping(source = "status", target = "userStatus")
    UserPO reqToPo(UserReq req);


    @Mapping(source = "id", target = "userId")
    @Mapping(source = "userStatus", target = "status")
    UserRes poToRes(UserPO po);

    @InheritConfiguration(name = "poToRes")
    UserRes poToRes2(UserPO po); // 继承上面的 @Mapping 设置 (有多个时要设置名字)

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userId", ignore = true)
    UserRes poToRes3(UserPO po); // 忽略属性

}
