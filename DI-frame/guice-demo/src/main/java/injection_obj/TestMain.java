package injection_obj;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestMain {

    public static void main( String[] args ) {
        Injector injector = Guice.createInjector( new MyAppModule() );
        MyApplication myApp = injector.getInstance( MyApplication.class );
        myApp.work();
        myApp.work();
    }

}
