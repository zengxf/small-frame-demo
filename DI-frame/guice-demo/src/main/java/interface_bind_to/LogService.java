package interface_bind_to;

public interface LogService {
    void log( String msg );
}

class LogServiceImpl implements LogService {
    @Override
    public void log( String msg ) {
        System.out.println( "------LOG: " + msg );
    }
}