package interface_bind_to;

public interface UserService {
    void process();
}

class UserServiceImpl implements UserService {
    @Override
    public void process() {
        System.out.println( "一些业务逻辑" );
    }
}