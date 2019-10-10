package test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test1 {

    public static void main( String[] args ) throws IOException, URISyntaxException {
        String content = ReadFiles.content( Paths.get( Test1.class.getResource( "/test1.html" )
                .toURI() ) );
        // log.info( content );
        Document root = Jsoup.parse( content );
        Element tableall = root.getElementById( "tableall" );
        Element rside = tableall.getElementById( "rside" );
        Elements arr = rside.select( "table tbody tr" );
        // log.info( "{}", arr.size() );
        // log.info( "{}", arr );
        IntStream.range( 1, arr.size() - 1 )
                .forEach( i -> {
                    Element tr = arr.get( i );
                    // log.info( "{}", tr );
                    Elements tds = tr.select( "td" );
                    String td = tds.stream()
                            .map( Element::ownText )
                            .collect( Collectors.joining( "\t" ) );
                    log.info( "{}", td );
                } );
    }

}
