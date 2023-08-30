package test.new_features.jdk1_8.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestFiles {

    public static void main( String[] args ) throws Throwable {

        // filesList();

        // filesFind();

        // filesWalk();

        // readAllAndWrite();

        // filesLines();

        // filesNewBufferedReader();

        // filesNewBufferedWriter();

        // probeContentType();

        getOwner();
    }

    static void getOwner() throws IOException {
        Path path = Paths.get( TestFiles.class.getResource( "txts/a.txt" ).getPath().replaceFirst( "/", "" ) );
        UserPrincipal principal = Files.getOwner( path );
        System.out.println( principal );
        System.out.println( principal.getName() );
    }

    static void probeContentType() throws IOException {
        Path path = Paths.get( TestFiles.class.getResource( "txts/a.txt" ).getPath().replaceFirst( "/", "" ) );
        String type = Files.probeContentType( path );
        System.out.println( type );
    }

    static void filesNewBufferedWriter() throws IOException {
        Path path = Paths.get( TestFiles.class.getResource( "txts/b.txt" ).getPath().replaceFirst( "/", "" ) );
        try ( BufferedWriter writer = Files.newBufferedWriter( path, StandardOpenOption.APPEND ) ) {
            writer.write( "print('Hello World');" );
        }
    }

    // 精细控制
    static void filesNewBufferedReader() throws IOException {
        Path path = Paths.get( TestFiles.class.getResource( "txts/a.txt" ).getPath().replaceFirst( "/", "" ) );
        try ( BufferedReader reader = Files.newBufferedReader( path ) ) {
            System.out.println( reader.readLine() );
        }
    }

    // 内存高效。不是一次性把所有行都读进内存。
    static void filesLines() throws IOException {
        Path curPath = Paths.get( TestFiles.class.getResource( "txts" ).getPath().replaceFirst( "/", "" ) );
        try ( Stream<String> stream = Files.lines( curPath.resolve( "a.txt" ) ) ) {
            stream//
                    .filter( line -> line.contains( "fuck" ) )//
                    .map( String::trim )//
                    .forEach( System.out::println );
        }
    }

    // 对内存并不十分高效，因为整个文件都会读进内存
    static void readAllAndWrite() throws IOException {
        Path curPath = Paths.get( TestFiles.class.getResource( "txts" ).getPath().replaceFirst( "/", "" ) );
        System.out.println( curPath );
        System.out.println( curPath.resolve( "a.txt" ) );
        List<String> lines = Files.readAllLines( curPath.resolve( "a.txt" ) );
        lines.add( "print('foobar');" );
        Files.write( curPath.resolve( "b.txt" ), lines );
    }

    static void filesWalk() throws IOException {
        Path start = Paths.get( "" );
        int maxDepth = 7;
        try ( Stream<Path> stream = Files.walk( start, maxDepth ) ) {
            String joined = stream//
                    .map( String::valueOf )//
                    .filter( path -> path.endsWith( ".txt" ) )//
                    .sorted()//
                    .collect( Collectors.joining( "; " ) );
            System.out.println( "walk(): " + joined );
        }
    }

    static void filesFind() throws IOException {
        Path start = Paths.get( "" );
        System.out.println( start.toFile().getAbsolutePath() );
        int maxDepth = 7;
        try ( Stream<Path> stream = Files.find( start, maxDepth, ( path, attr ) -> //
        String.valueOf( path ).endsWith( ".txt" ) ) ) {
            String joined = stream.sorted()//
                    .map( String::valueOf )//
                    .collect( Collectors.joining( "; " ) );
            System.out.println( "Found: " + joined );
        }
    }

    static void filesList() throws IOException {
        try ( Stream<Path> stream = Files.list( Paths.get( "" ) ) ) {
            String joined = stream//
                    .map( String::valueOf )//
                    .filter( path -> !path.startsWith( "." ) )//
                    .sorted()//
                    .collect( Collectors.joining( "; " ) );
            System.out.println( "List: " + joined );
        }
    }

}
