package test.jdkapi.serial;

import java.io.IOException;
import java.io.Serializable;

import lombok.Data;

@Data
public class TestADto implements Serializable {

    private transient TestBDto dto;
    private String             name;

    private static final long  serialVersionUID = 1L;

    private void writeObject( java.io.ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();
        out.writeUTF( dto.getName() );
    }

    private void readObject( java.io.ObjectInputStream in ) throws ClassNotFoundException, IOException {
        dto = new TestBDto();
        in.defaultReadObject();
        String name = in.readUTF();
        dto.setName( name );
    }

}
