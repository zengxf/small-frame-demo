package cn.zxf.test.test1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

/**
 * Created by zengxf on 2019/8/28.
 */
public class Test1Controller {

    @FXML
    CheckBox myCheck;

    public void setValue(boolean value) {
        myCheck.setSelected(value);
    }

    public void onOk(ActionEvent event) {
        System.out.println("确认-关闭 == " + myCheck.isSelected());
    }

    public void onCancel(ActionEvent event) {
        System.out.println("取消-关闭!");
    }

}
