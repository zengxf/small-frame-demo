package cn.zxf.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class VerticleToVertx {
    public static void main( String[] args ) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( new MyVerticle() );
    }

    static class MyVerticle extends AbstractVerticle {

        @Override
        public void start( Future<Void> startFuture ) {
            System.out.println( "MyVerticle started!" );
        }

        @Override
        public void stop( Future<Void> stopFuture ) throws Exception {
            System.out.println( "MyVerticle stopped!" );
        }

    }
}
