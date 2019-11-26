package injection_obj;

import com.google.inject.AbstractModule;

public class MyAppModule extends AbstractModule {

    @Override
    protected void configure() {
        super.requestInjection( new MyApplication() );
    }

}