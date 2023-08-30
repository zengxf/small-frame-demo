package test.string.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 统计英文单词数
 * <p>
 * Created by zengxf on 2019-02-22
 */
// M:\project\zxf_super_demo\simple-demo\bin
// java cn.simple.test.string.file.StatsWordNumber
public class StatsWordNumber {

    static String path = System.getProperty( "path", "C:\\Users\\Administrator\\Desktop\\en.txt" );

    public static void main( String[] args ) throws IOException {
        Scanner sc = new Scanner( System.in );
        String sign;
        do {
            stats();
            System.out.print( "是否重新加载统计：" );
            sign = sc.nextLine();
        } while ( "yY".contains( sign ) );
        sc.close();
    }

    static void stats() throws FileNotFoundException, IOException {
        File file = new File( path );
        if ( !file.exists() ) {
            System.out.println( "文件不存在！path: " + path );
            return;
        }
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( path ) ) );

        int sum = 0;
        Set<String> set = new LinkedHashSet<>();

        String line;
        while ( ( line = br.readLine() ) != null ) {
            line = line.trim();
            if ( line.isEmpty() )
                continue;
            String[] arr = line.split( "[^a-zA-Z\\-]+" );
            sum += arr.length;
            Stream.of( arr )
                    .forEach( set::add );
        }

        br.close();

        System.out.println( "\n总数：" + sum );
        System.out.println( "去重后总数：" + set.size() );

        String[] arr = set.toArray( new String[] {} );
        for ( int i = 0; i < arr.length; i++ ) {
            if ( i % 10 == 0 )
                System.out.println();
            System.out.print( arr[i] + " " );
        }
        System.out.println( "\r\n" );
    }

}
