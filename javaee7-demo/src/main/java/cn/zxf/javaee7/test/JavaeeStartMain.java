package cn.zxf.javaee7.test;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * -javaagent:L:/IDE/Java/Plugin/xrebel/xrebel-3.4.1.jar
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class JavaeeStartMain {

    static final int PORT = 9090;

    public static void main( String[] args ) throws Exception {
        String webDir = JavaeeStartMain.class.getResource( "/webapp" ).getPath();
        String classDir = JavaeeStartMain.class.getResource( "/" ).getPath();

        System.out.println( "configuring app with basedir: " + webDir );
        System.out.println( "configuring app with classDir: " + classDir );

        Tomcat tomcat = new Tomcat();
        tomcat.setPort( PORT );
        Context ctx = tomcat.addWebapp( "/", webDir );

        WebResourceRoot resources = new StandardRoot( ctx );
        resources.addPreResources( new DirResourceSet( resources, "/WEB-INF/classes", classDir, "/" ) );
        ctx.setResources( resources );

        tomcat.start();
        tomcat.getServer().await();
    }

}
