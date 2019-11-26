package interface_bind_to;

import javax.inject.Inject;

public interface Application {
    void work();
}

class MyApp implements Application {
    private UserService userService;
    private LogService  logService;
    @Inject
    private TestService test;

    @Inject
    public MyApp( UserService userService, LogService logService ) {
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public void work() {
        userService.process();
        logService.log( "程序正常运行" );
        test.test();
    }
}
