package cn.zxf.apt.demo;

@Route( "/test" )
public class RouteService {

    public void onCreate( String savedInstanceState ) {
        System.out.println( "onCreate = " + savedInstanceState );
    }

}
