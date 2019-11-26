package injection_obj;

import javax.inject.Inject;

public class MyApplication {
    @Inject
    private TestService test;

    public void work() {
        System.out.println( "test == " + test );
        test.test();
    }
}
