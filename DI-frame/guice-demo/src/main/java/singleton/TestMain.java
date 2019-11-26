package singleton;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton // 打上了这个标记说明是单例的，否则 Guice 每次回返回一个新的对象
class UserDao {
    public void say() {
        System.out.println( "dao is saying" );
    }
}

// @Singleton
class UserService {
    // @com.google.inject.Inject
    @javax.inject.Inject
    private UserDao dao;

    public void say() {
        System.out.println( "dao == " + dao );
        dao.say();
    }
}

public class TestMain {
    public static void main( String[] args ) {
        Injector injector = Guice.createInjector();

        UserService service = injector.getInstance( UserService.class );
        System.out.println( "service == " + service );
        service.say();

        service = injector.getInstance( UserService.class );
        System.out.println( "service == " + service );
        service.say();
    }
}
