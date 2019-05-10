package cn.zxf.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start( Stage primaryStage ) {
        try {
            Parent root = FXMLLoader.load( Main.class.getResource( "/simple.fxml" ) );
            primaryStage.setTitle( "JavaFX 简单测试" );
            primaryStage.setScene( new Scene( root ) );
            primaryStage.show();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        launch( args );
    }

}