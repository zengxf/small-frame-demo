package test.new_features.jdk1_7.nio2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * {@link Files#move}与{@link java.io.File#renameTo}有点类似
 * 
 * <p>
 * Created by zengxf on 2017-08-14
 */
public class TestFiles {
    public static void main( String[] args ) throws IOException {
        // test_copy();
        // test_createDirectory();
        // test_exists();
        test_walkFileTree();
    }

    static void test_walkFileTree() throws IOException {
        Files.walkFileTree( Paths.get( "" ), new SimpleFileVisitor<Path>() {
        } );

        { // 递归删除目录，在visitFile()接口中删除所有文件，最后在postVisitDirectory()内删除目录本身
            Path rootPath = Paths.get( "data/to-delete" );
            try {
                Files.walkFileTree( rootPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile( Path file, BasicFileAttributes attrs ) throws IOException {
                        System.out.println( "delete file: " + file.toString() );
                        Files.delete( file );
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory( Path dir, IOException exc ) throws IOException {
                        System.out.println( "delete dir: " + dir.toString() );
                        Files.delete( dir );
                        return FileVisitResult.CONTINUE;
                    }
                } );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    static void test_exists() {
        Path path = Paths.get( "data/logging.properties" );
        boolean pathExists = Files.exists( path, LinkOption.NOFOLLOW_LINKS ); // 表示检测时不包含符号链接文件
        System.out.println( pathExists );
    }

    static void test_createDirectory() throws IOException {
        Files.createDirectories( Paths.get( "D:/test/jdk17", "aa/bb" ) );
        Files.createDirectory( Paths.get( "D:/test/jdk17", "bb" ) );
    }

    static void test_copy() throws FileNotFoundException, IOException {
        Path target = Paths.get( "D:/test/jdk17", "bbb.txt" );
        InputStream in = new FileInputStream( "D:/test/jdk17/aaa.txt" );
        Files.copy( in, target, StandardCopyOption.REPLACE_EXISTING );
    }
}
