import cn.simple.test.new_features.jdk9.jl.TestPlatformLogger;

module cn.zxf.j9.base.test {
    requires jdk.incubator.httpclient;
    requires java.prefs;
    requires java.compiler;
    requires java.management;

    provides java.lang.System.LoggerFinder with TestPlatformLogger.Log4jLoggerFinder;
}