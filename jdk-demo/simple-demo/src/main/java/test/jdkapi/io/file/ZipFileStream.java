package test.jdkapi.io.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ZipFileStream {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\aaa\\zip.zip");

        // -------------
        try (FileSystem fs = FileSystems.newFileSystem(path, ZipFileStream.class.getClassLoader())) { // FS 要及时关闭
            System.out.println("-------------");
            Files.walk(fs.getPath("/"))
                    .forEach(System.out::println);
            System.out.println("-------------");
            Files.readAllLines(fs.getPath("/zip/aa.txt"))
                    .forEach(System.out::println);
        }

        // -------------
        URI uri = new URI("jar", path.toUri()
                .toString(), null); // Constructs the URI jar:file://myfile.zip
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, //
                Collections.singletonMap("create", "true"))) {
            Files.write(zipfs.getPath("/")
                    .resolve("cc.txt"), "test\ntest11".getBytes());
        }

    }

}
