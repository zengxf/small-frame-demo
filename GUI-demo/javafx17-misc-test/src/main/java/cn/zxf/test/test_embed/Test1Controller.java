package cn.zxf.test.test_embed;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by zengxf on 2019/8/28.
 */
public class Test1Controller {

    @FXML
    TextField myText;

    public void setText(String value) {
        myText.setText(value);
    }

    public void onInnerTest1() {
        System.out.println("test inner 1111");
    }

}
