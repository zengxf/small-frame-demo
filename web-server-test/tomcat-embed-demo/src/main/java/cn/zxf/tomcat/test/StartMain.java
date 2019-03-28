package cn.zxf.tomcat.test;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class StartMain {

    static final int PORT = 9091;

    public static void main( String[] args ) throws Exception {
        String webDir = StartMain.class.getResource( "/webapp" ).getPath();
        String classDir = StartMain.class.getResource( "/" ).getPath();

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
