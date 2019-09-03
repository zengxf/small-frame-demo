open module cn.zxf.javafx.rest_test {
    requires java.sql;

    requires javafx.fxml;
    requires javafx.controls;

    requires retrofit2;
    requires retrofit2.converter.scalars;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okhttp3.logging;

    requires static lombok;
}