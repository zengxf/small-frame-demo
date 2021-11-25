package cn.zxf.test.test_embed;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * Created by zengxf on 2019/8/28.
 */
public class TestEmbedController {

    @FXML
    Pane inner1;
    @FXML
    Test1Controller inner1Controller; // $id + Controller
    @FXML
    Pane inner2;
    @FXML
    Test2Controller inner2Controller; // $id + Controller

    public void onTest1() {
        inner1Controller.setText("====== test 1111");
        System.out.println("test 1111");
    }

    public void onTest2() {
        System.out.println("test 2222");
        inner2Controller.setText("====== test 2222");
    }

}
