package test.new_features.jdk1_7.nio2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件目录操作监听
 * 
 * <p>
 * Created by zxf on 2017-06-30
 */
@Slf4j
public class TestFileWatch {

    public static void main( String[] args ) throws IOException {

        Path path = Paths.get( "C:/Users/Administrator/Desktop" );
        WatchService watcher = FileSystems.getDefault()
                .newWatchService();
        path.register( watcher, StandardWatchEventKinds.ENTRY_MODIFY ); // 只监听修改

        new Thread( () -> {
            while ( true ) {
                try {
                    WatchKey key = watcher.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    events.forEach( event -> {
                        log.info( "watch event: {} - {}", event.kind(), event.context() );
                    } );
                    key.reset();
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        } ).start();

    }

}
