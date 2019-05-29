package cn.zxf.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

    @Expose
    private String  userName;

    @Since( 2.6D )
    @SerializedName( "user-age" )
    private Integer age;

    @Until( 2.8D )
    private String  sex;

}
