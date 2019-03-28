package cn.zxf.redis_mongo.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document( collection = "test_user" )
public class User {

    @Id
    private String  id;
    private String  name;
    private Integer status;

}
