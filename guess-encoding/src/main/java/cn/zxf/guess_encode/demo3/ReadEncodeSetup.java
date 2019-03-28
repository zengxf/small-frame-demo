package cn.zxf.guess_encode.demo3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadEncodeSetup {

    public static void main( String[] args ) throws IOException {
        String regex = "encoding//(.+?)=(.+)";
        Pattern p = Pattern.compile( regex );
        Path path = Paths.get( "M:\\project\\zxf_super_demo\\simple-demo\\.settings\\org.eclipse.core.resources.prefs" );
        Files.readAllLines( path ) //
                .stream() //
                .filter( line -> line.matches( regex ) ) //
                .forEach( line -> {
                    Matcher m = p.matcher( line );
                    if ( m.find() ) {
                        // System.out.println( m.group( 1 ) + " ==> " + m.group( 2 ) );
                        String str = String.format( "pathOfEncode.put( \"%s\", \"%s\" );", m.group( 1 ), m.group( 2 ) );
                        System.out.println( str );
                    }
                } );
    }

}
