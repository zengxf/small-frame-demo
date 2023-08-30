package test.jdkapi.serial;

import java.io.Serializable;

import lombok.Data;

@Data
public class SerialDto implements Serializable {

    private static final long serialVersionUID = -1653384214844337193L;

    static final String       sign             = "v3.0";

    private String            name;
    private final String      version          = init();
    private final Integer     testa            = 5;

    public SerialDto() {
        super();
    }

    private String init() {
        return "3.33";
    }

}
