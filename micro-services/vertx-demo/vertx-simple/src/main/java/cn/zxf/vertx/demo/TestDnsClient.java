package cn.zxf.vertx.demo;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.dns.DnsClient;

public class TestDnsClient {

    public static void main( String[] args ) {
        Vertx vertx = Vertx.vertx();

        // DnsClient client = vertx.createDnsClient( 53, "8.8.8.8" );
        DnsClient client = vertx.createDnsClient( 53, "9.9.9.9" );

        client.lookup4( "vertx.io", ar -> {
            if ( ar.succeeded() ) {
                System.out.println( "lookup4: " + ar.result() );
            } else {
                System.out.println( "Failed to resolve entry " + ar.cause() );
                ar.cause()
                        .printStackTrace();
            }
        } );

        client.resolveA( "vertx.io", ar -> {
            if ( ar.succeeded() ) {
                List<String> records = ar.result();
                for ( String record : records ) {
                    System.out.println( "resolveA: " + record );
                }
            } else {
                System.out.println( "Failed to resolve entry" + ar.cause() );
            }

            vertx.close();
        } );
    }

}
