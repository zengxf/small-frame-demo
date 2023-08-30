package test.jdkapi.io.object;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 将对象格式化成流并加密特殊字段
 */
public class TestCryptObject {

    public static void main( String[] args ) throws Exception {
        String file = "C:\\Users\\Administrator\\Desktop\\aa\\test\\obj.dat";

        ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( file ) );
        Test obj = new Test( "1", "zxf", "ttt" );
        oos.writeObject( obj );
        oos.close();
        System.out.println( obj );

        ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) );
        Test t = (Test) ois.readObject();
        ois.close();
        System.out.println( t );
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test implements Externalizable {
        private String id;
        private String name;
        private String pwd;

        @Override
        public void writeExternal( ObjectOutput out ) throws IOException {
            out.writeUTF( id );
            out.writeUTF( name );
            out.writeUTF( "$" + pwd ); // 加密
        }

        @Override
        public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException {
            id = in.readUTF();
            name = in.readUTF();
            String pwdOld = in.readUTF();
            System.out.println( "解密：" + pwdOld );
            pwd = pwdOld.substring( 1 ); // 解密
        }
    }

}
