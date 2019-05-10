package cn.zxf.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MyController implements Initializable {
    @FXML
    private Button showDateButton;
    @FXML
    private TextField dateText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");

        DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String dateTimeString = df.format(new Date());

        dateText.setText(dateTimeString);
    }

}
