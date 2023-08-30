package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;

/**
 * 序列化工具类
 * 
 * @author zxf
 */
@Slf4j
public class SerialUtils {

    public static < T extends Serializable > void writeObject( T obj, String filePath ) {
        try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( filePath ) ) ) {
            oos.writeObject( obj );
        } catch ( IOException e ) {
            log.error( "序列化对象到文件时出错！", e );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static < T extends Serializable > T readObject( String filePath ) {
        try ( ObjectInputStream ois = new ObjectInputStream( new FileInputStream( filePath ) ) ) {
            Object obj = ois.readObject();
            return (T) obj;
        } catch ( IOException | ClassNotFoundException e ) {
            log.error( "反序列化文件到对象时出错！", e );
        }
        return null;
    }

}
