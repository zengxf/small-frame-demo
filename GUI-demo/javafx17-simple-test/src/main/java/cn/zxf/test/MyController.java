package cn.zxf.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyController /*implements Initializable*/ {
//    @FXML
//    private Button showDateButton;
    @FXML
    private TextField dateText;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//    }

    public void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");

        DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String dateTimeString = df.format(new Date());

        dateText.setText(dateTimeString);
    }

}
