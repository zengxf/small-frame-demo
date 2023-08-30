package test.jvm.reload_class.agent;
//package cn.simple.test.reload_class.agent;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.lang.instrument.ClassDefinition;
//import java.lang.instrument.Instrumentation;
//import java.lang.instrument.UnmodifiableClassException;
//import java.lang.management.ManagementFactory;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import com.sun.tools.attach.AgentInitializationException;
//import com.sun.tools.attach.AgentLoadException;
//import com.sun.tools.attach.AttachNotSupportedException;
//import com.sun.tools.attach.VirtualMachine;
//
//public class JavaAgent {
//
//    public static final Logger    logger = Logger.getLogger( "test-reload" );
//
//    private static String         classesPath;
//    private static String         jarPath;
//    private static VirtualMachine vm;
//    private static String         pid;
//
//    static {
//        classesPath = JavaAgent.class.getClassLoader()
//                .getResource( "" )
//                .getPath();
//        logger.log( Level.INFO, "java agent:jarPath:" + classesPath );
//        jarPath = getJarPath();
//        logger.log( Level.INFO, "java agent:jarPath:" + jarPath );
//
//        // 当前进程pid
//        String name = ManagementFactory.getRuntimeMXBean()
//                .getName();
//        pid = name.split( "@" )[0];
//        logger.log( Level.INFO, "当前进程pid：" + pid );
//    }
//
//    /**
//     * 获取jar包路径
//     * 
//     * @return
//     */
//    public static String getJarPath() {
//        // StringUtils是jar文件内容
//        URL url = JavaAgent.class.getProtectionDomain()
//                .getCodeSource()
//                .getLocation();
//        String filePath = null;
//        try {
//            filePath = URLDecoder.decode( url.getPath(), "utf-8" );// 转化为utf-8编码
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//        if ( filePath.endsWith( ".jar" ) ) {// 可执行jar包运行的结果里包含".jar"
//            // 截取路径中的jar包名
//            filePath = filePath.substring( 0, filePath.lastIndexOf( "/" ) + 1 );
//        }
//
//        File file = new File( filePath );
//
//        filePath = file.getAbsolutePath();// 得到windows下的正确路径
//        return filePath;
//    }
//
//    private static void init() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
//        // 虚拟机加载
//        vm = VirtualMachine.attach( pid );
//        vm.loadAgent( jarPath + "/javaagent.jar" );
//
//        Instrumentation instrumentation = JavaDynAgent.getInstrumentation();
//        if ( instrumentation == null ) {
//            System.out.println( "instrumentation null point ..." );
//        }
//    }
//
//    private static void destroy() throws IOException {
//        if ( vm != null ) {
//            vm.detach();
//        }
//    }
//
//    /**
//     * 重新加载类
//     *
//     * @param classArr
//     * @throws Exception
//     */
//    public static void javaAgent( String root, String[] classArr ) throws ClassNotFoundException, IOException, UnmodifiableClassException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
//        init();
//
//        try {
//            // 1.整理需要重定义的类
//            List<ClassDefinition> classDefList = new ArrayList<ClassDefinition>();
//            for ( String className : classArr ) {
//                Class<?> c = Class.forName( className );
//                String classPath = ( root != null && root.trim()
//                        .length() > 0 ? root : classesPath ) + className.replace( ".", "/" ) + ".class";
//                logger.log( Level.INFO, "class redefined:" + classPath );
//                byte[] bytesFromFile = toByteArray( new File( classPath ) );
//                ClassDefinition classDefinition = new ClassDefinition( c, bytesFromFile );
//                classDefList.add( classDefinition );
//            }
//            // 2.redefine
//            JavaDynAgent.getInstrumentation()
//                    .redefineClasses( classDefList.toArray( new ClassDefinition[classDefList.size()] ) );
//        } finally {
//            destroy();
//        }
//    }
//
//    private static byte[] toByteArray( File file ) throws IOException {
//        byte[] arr = new byte[(int) file.length()];
//        FileInputStream fis = new FileInputStream( file );
//        fis.read( arr, 0, arr.length );
//        fis.close();
//        return arr;
//    }
//
//    public static void main( String[] args ) throws Exception {
//
//        TargetFunction.testStatic();
//        javaAgent( null, new String[] { TargetFunction.class.getName() } );
//        TargetFunction.testStatic();
//
//    }
//}
