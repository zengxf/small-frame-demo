package cn.zxf.resume_parse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class TestSimple {

    public static String resumeParserJson( String url, String fname, String client_id, String client_secret ) throws Exception {
        byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray( new File( fname ) );
        String data = new String( Base64.encodeBase64( bytes ), Consts.UTF_8 );

        HttpPost httpPost = new HttpPost( url );
        httpPost.setEntity( new StringEntity( data, Consts.UTF_8 ) );

        // 设置头字段
        httpPost.setHeader( "id", client_id );
        httpPost.setHeader( "secret", client_secret );
        httpPost.addHeader( "content-type", "application/json" );

        // 设置内容信息
        JSONObject json = new JSONObject();
        json.put( "file_name", fname ); // 文件名
        json.put( "resume_base", data ); // 经base64编码过的文件内容
        // json.put( "rawtext", 1 ); //
        StringEntity params = new StringEntity( json.toString() );
        httpPost.setEntity( params );

        // 发送请求
        HttpClient httpclient = HttpClientBuilder.create()
                .build();
        HttpResponse response = httpclient.execute( httpPost );

        // 处理返回结果
        String resCont = EntityUtils.toString( response.getEntity(), Consts.UTF_8 );
        // System.out.println( resCont );

        JSONObject res = new JSONObject( resCont );
        return res.toString( 4 );
    }

    public static void main( String[] args ) throws Exception {
        String url = "http://api.littleparser.com/v1/parser/parse_base?rawtext=1";
        String client_id = "e6b07c80-f45a-11e8-bc97-69733596546f"; // 替换为您的ID
        String client_secret = "5d609daa-219c-42e7-a0c7-305e9a20d86e"; // 替换为您的密匙

        Files.find( Paths.get( "D:\\test\\resume-test" ), 1, ( path, fileAttr ) -> {
            String name = path.getFileName()
                    .toString();
            return !fileAttr.isDirectory() && ( name.endsWith( ".pdf" ) || name.endsWith( ".docx" ) );
        } )
                .forEach( file -> {
                    String fname = file.toFile()
                            .getPath(); // 替换为您的文件名
                    try {
                        String json = resumeParserJson( url, fname, client_id, client_secret );
                        FileUtils.write( new File( fname + ".json" ), json, "UTF-8" );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                } );
    }
}
