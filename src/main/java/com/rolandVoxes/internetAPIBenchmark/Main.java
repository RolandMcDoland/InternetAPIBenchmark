package com.rolandVoxes.internetAPIBenchmark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./MainWindow.fxml"));
        primaryStage.setTitle("APIBenchmark");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        //primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

//    public static void main(String[] args) {
//        RequestController requestController = new RequestController();
//        TimerController timerController = new TimerController();
//        String url = "https://api.chucknorris.io/jokes/random";
//
//        MockModel previousMockModel = JsonController.load("data.json", MockModel.class);
//        System.out.println(previousMockModel);
//
//        timerController.startTimer();
//        requestController.sendRequest(url);
//        timerController.stopTimer();
//        float duration = timerController.getDuration();
//        System.out.println(duration);
//
//        MockModel mockModel = new MockModel(url, duration);
//        JsonController.save(mockModel, "data.json");
//    }
}
