package test.jdkapi.serial;

import java.io.Serializable;

import lombok.Data;

@Data
public class TestBDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String            name;
    private String            msg;

}
