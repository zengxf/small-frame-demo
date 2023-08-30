package test.new_features.jdk1_7.nio2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * "/test.txt" 在 windows 下相对于当前磁盘下的路径。<br>
 * 如：M:/test.txt
 * 
 * <p>
 * Created by zengxf on 2017-08-09
 */
public class TestPath {

    public static void main( String[] args ) throws IOException {
        Path p = Paths.get( "/a-test.txt" );
//        Files.write( p, "fuck".getBytes() );
        System.out.println( p.toAbsolutePath() );

        // absolute
        Path currentDir = Paths.get( "." );
        System.out.println( currentDir.toAbsolutePath() );
        System.out.println( Paths.get( "D:/a/./b.txt" ).toAbsolutePath() );

        // normalize
        Path parentDir2 = Paths.get( "d:\\data\\projects\\a-project\\..\\another-project" );
        System.out.println( parentDir2.toAbsolutePath() );
        System.out.println( parentDir2.normalize().toAbsolutePath() );
        System.out.println( Paths.get( ".." ).normalize().toAbsolutePath() );
    }

}
