package interface_bind_to;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestMain {

    public static void main( String[] args ) {
        Injector injector = Guice.createInjector( new MyAppModule() );
        Application myApp = injector.getInstance( Application.class );
        myApp.work();
    }

}
