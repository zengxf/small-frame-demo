package test;

import io.bootique.Bootique;

public class TestMain {

    public static void main( String[] args ) {
        Bootique.app( args )
                .autoLoadModules()
                .exec()
                .exit();
    }

}
