package test;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors( chain = true )
public class User {
    private String       name;
    private String       pwd;
    private int          age;
    private String       address;
    private String       note;
    private List<String> tags;
}
