package test.socket.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class ApiTest {
    public static void main( String[] args ) throws MalformedURLException, IOException {

        String userId = "zxf";
        String[] keys = { "制造", "能源" }; // "测试", "开发", "运营", "搬砖", "设计"
        for ( int i = 0; i < 100; i++ ) {
            int r = new Random().nextInt( keys.length );
            int md = i % ( r + 1 );
            String uid = userId + md;
            String key = keys[r] + md;
            key = URLEncoder.encode( key, "UTF8" );

            String param = String.format( "userId=%s&keyword=%s", uid, key );

            String url = "http://localhost:8090/api/search_history/insert?" + param;

            new URL( url ).openConnection().getInputStream();
        }

    }
}
