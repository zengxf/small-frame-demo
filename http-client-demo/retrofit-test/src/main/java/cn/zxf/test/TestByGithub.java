package cn.zxf.test;

import java.io.IOException;
import java.util.List;

import lombok.Data;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class TestByGithub {

    public static void main( String[] args ) throws IOException {
        Retrofit retrofit = new Retrofit.Builder() //
                .baseUrl( "https://api.github.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
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
