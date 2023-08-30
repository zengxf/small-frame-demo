package test.string.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 解析月记
 * <p>
 * Created by zengxf on 2019-06-19
 */
@Slf4j
public class ParseMonthNote {

    private String file;
    private String month;

    public static ParseMonthNote of( String file ) {
        ParseMonthNote parser = new ParseMonthNote();
        parser.file = file;
        return parser;
    }

    public void parse() throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( this.file ), "UTF-8" ) );
        String line;
        int i = 0;
        boolean day = false;
        List<String> list = new ArrayList<>();
        while ( ( line = br.readLine() ) != null ) {
            line = line.trim();
            if ( i++ == 0 ) {
                this.parseMonth( line );
            } else if ( line.startsWith( "## " ) ) {
                day = true;
                list.add( line );
            } else if ( day ) {
                if ( line.equals( "---" ) ) {
                    this.parseDayNote( list );
                    day = false;
                    list.clear();
                } else {
                    list.add( line );
                }
            }
        }
        this.parseDayNote( list );
        br.close();
    }

    private void parseDayNote( List<String> list ) {
        String first = list.get( 0 );
        String day = parseDay( first );
        log.info( "day-note: {}", day );
        int i = 1;
        while ( !list.get( i )
                .startsWith( "### " ) )
            i++;
        String second = list.get( i++ );
        String typeV = parseValue( second, "### (.+)", "类型格式不对：" );
        log.info( "day-note-typeV: {}", typeV );
        List<String> items = new ArrayList<>();
        for ( ; i < list.size(); i++ ) {
            String line = list.get( i );
            if ( line.startsWith( "### " ) ) {
                this.parseTypeNote( items );
                items.clear();
                typeV = parseValue( line, "### (.+)", "类型格式不对：" );
                log.info( "day-note-typeV: {}", typeV );
            } else
                items.add( line );
        }
        this.parseTypeNote( items );
    }

    private void parseTypeNote( List<String> list ) {
        String first = list.get( 0 );
        String title = parseValue( first, "#### (.+)", "标题格式不对：" );
        log.info( "title: {}", title );
        List<String> items = new ArrayList<>();
        for ( int i = 1; i < list.size(); i++ ) {
            String line = list.get( i );
            if ( line.startsWith( "#### " ) ) {
                title = parseValue( line, "#### (.+)", "标题格式不对：" );
                log.info( "title: {}", title );
            } else
                items.add( line );
        }
    }

    private String parseDay( String str ) {
        return parseValue( str, ".*?(\\d{2})", "日期格式不对：" );
    }

    private void parseMonth( String str ) {
        month = parseValue( str, ".*?(\\d{4}\\-\\d{2})", "月份格式不对：" );
        log.info( "month: {}", month );
    }

    private String parseValue( String str, String regex, String errMsg ) {
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( str );
        if ( matcher.find() ) {
            return matcher.group( 1 );
        } else {
            throw ParseMonthNoteException.of( errMsg + str );
        }
    }

    public static class ParseMonthNoteException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ParseMonthNoteException( String message ) {
            super( message );
        }

        public static ParseMonthNoteException of( String message ) {
            return new ParseMonthNoteException( message );
        }
    }

    public static void main( String[] args ) throws IOException {
        String file = "C:\\Users\\Administrator\\Desktop\\aa\\month-note\\test.md";
        ParseMonthNote.of( file )
                .parse();
    }

}
