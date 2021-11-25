package test;

import feign.Param;
import feign.RequestLine;
import lombok.Data;

import java.util.List;

/**
 * GitHub 接口测试
 * <br/>
 * Created by ZXFeng on  2021/11/25.
 */
public interface GitHub {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(
            @Param("owner") String owner,
            @Param("repo") String repo
    );

    @Data
    class Contributor {
        String login;
        int contributions;
    }

}
