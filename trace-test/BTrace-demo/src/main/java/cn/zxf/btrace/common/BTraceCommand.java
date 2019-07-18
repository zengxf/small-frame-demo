package cn.zxf.btrace.common;

import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 其他 Demo 帮助类
 * <p>
 * Created by zengxf on 2019-07-18
 */
public class BTraceCommand {

    public static void print() {
        System.out.println( curPath() );
        System.out.println( command() );
    }

    public static void execute() {
        Runtime rt = Runtime.getRuntime();
        try {
            String cmd = String.format( "cd /d %s", curPath() );
            System.out.println( "\n\n" + cmd );
            System.out.println( "------------" );
            System.out.println( command() + "\n\n" );
            rt.exec( new String[] { "cmd.exe", "/c", cmd + " && start" } );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    static String command() {
        String main = getMain();
        String pid = getPid();
        String command = String.format( "btrace %s %sScript.java", pid, main );
        return command;
    }

    static String curPath() {
        try {
            String cp = getMainPath();
            Path p = Paths.get( BTraceCommand.class.getResource( "/" )
                    .toURI() );
            String path = p.resolve( "../src/main/java/" + cp )
                    .normalize()
                    .toAbsolutePath()
                    .toString();
            return path;
        } catch ( URISyntaxException e ) {
            e.printStackTrace();
            throw new RuntimeException( "获取路径出错", e );
        }
    }

    static String getPid() {
        String s = ManagementFactory.getRuntimeMXBean()
                .getName();
        return s.split( "@" )[0];
    }

    static String getMain() {
        String name = getMainClass();
        String[] arr = name.split( "\\." );
        return arr[arr.length - 1];
    }

    static String getMainPath() {
        String name = getMainClass();
        String[] arr = name.split( "\\." );
        return Stream.of( arr )
                .limit( arr.length - 1 )
                .collect( Collectors.joining( "/" ) );
    }

    static String getMainClass() {
        String name = new Exception().getStackTrace()[4].getClassName();
        return name;
    }

}
