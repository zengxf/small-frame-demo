package cn.zxf.vertx.demo;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class TestConfig {

    public static void main( String[] args ) {
        ConfigStoreOptions propertyWitHierarchical = new ConfigStoreOptions().setFormat( "properties" )
                .setType( "file" )
                .setConfig( new JsonObject().put( "path", "application.properties" ) );
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore( propertyWitHierarchical );

        ConfigRetriever configRetriever = ConfigRetriever.create( Vertx.vertx(), options );

        configRetriever.configStream()
                .handler( config -> {
                    String host = config.getString( "server.host" );
                    System.out.println( host );

                    Integer port = config.getInteger( "server.port" );
                    System.out.println( port );
                } );
    }

}
