package cn.zxf.test;

import java.io.IOException;
import java.util.List;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class TestLoggingByGithub {

    public static void main( String[] args ) throws IOException {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY ); // 设定日志级别
        interceptor.setLevel( HttpLoggingInterceptor.Level.BASIC );
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor( interceptor ); // 添加拦截器

        Retrofit retrofit = new Retrofit.Builder() //
                .baseUrl( "https://api.github.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .client( okHttpClient.build() )
                .build();
        GitHubService service = retrofit.create( GitHubService.class );
        System.out.println( service.getClass() );

        Call<List<Repo>> call = service.listRepos( "zengxf" );
        List<Repo> res = call.execute()
                .body();
        res.forEach( repo -> {
            System.out.println( repo );
        } );
        System.out.println( call.isExecuted() );
    }

    static interface GitHubService {
        @GET( "users/{user}/repos" )
        Call<List<Repo>> listRepos( @Path( "user" ) String user );
    }

    @Data
    static class Repo {
        Integer id;
        String  name;
        String  description;
        Boolean fork;
        Integer size;
        String  defaultBranch;
    }

}
