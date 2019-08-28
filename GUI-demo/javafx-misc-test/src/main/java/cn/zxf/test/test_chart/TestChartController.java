package cn.zxf.test.test_chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/**
 * Created by zengxf on 2019/8/28.
 */
public class TestChartController {

    @FXML
    BarChart<String, Integer> barChart;
    @FXML
    CategoryAxis xAxis;
    @FXML
    PieChart pieChart;

    ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.CHINA).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("每月嫖资");
        for (int i = 0; i < monthNames.size(); i++) {
            int value = 20 + new Random().nextInt(50);
            series.getData().add(new XYChart.Data<>(monthNames.get(i), value));
        }
        barChart.getData().add(series);

        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        series2.setName("每月赌资");
        for (int i = 0; i < monthNames.size(); i++) {
            int value = 40 + new Random().nextInt(30);
            series2.getData().add(new XYChart.Data<>(monthNames.get(i), value));
        }
        barChart.getData().add(series2);

        pieChart.getData().add(new PieChart.Data("投资", 3233.32));
        pieChart.getData().add(new PieChart.Data("吃", 4033.89));
        pieChart.getData().add(new PieChart.Data("喝嫖赌", 5897.45));

        this.initializeEvent();
    }

    private void initializeEvent() {
        for (XYChart.Series<String, Integer> series : barChart.getData()) {
            for (XYChart.Data<String, Integer> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(series.getName() + "【" + data.getXValue() + "：" + data.getYValue() + "】");
                Tooltip.install(data.getNode(), tooltip);
            }
        }
        for (PieChart.Data data : pieChart.getData()) {
            Tooltip tooltip = new Tooltip();
            tooltip.setText("值：" + data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);
        }
    }

}
