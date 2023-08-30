package test.illustration.lombok_annotation.proxy;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor( staticName = "of" )
public class TestImpl implements ITest {

    private String sign;

    @Override
    public void test( String name ) {
        System.out.printf( "test => %s == %s %n", this.sign, name );
    }

    @Override
    public int get( String name ) {
        System.out.printf( "get => %s == %s %n", this.sign, name );
        return 10;
    }

    @Override
    public List<String> find() {
        return List.of( "a", "b", "c" );
    }

}
