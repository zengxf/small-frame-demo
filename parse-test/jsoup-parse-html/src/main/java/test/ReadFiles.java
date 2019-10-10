package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ReadFiles {

    public static String content( Path path ) throws IOException {
        return Files.readAllLines( path )
                .stream()
                .collect( Collectors.joining( "\n" ) );
    }

}
