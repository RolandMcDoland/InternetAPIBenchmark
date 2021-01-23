package com.rolandVoxes.internetAPIBenchmark.ui;

import com.rolandVoxes.internetAPIBenchmark.controller.JsonController;
import com.rolandVoxes.internetAPIBenchmark.controller.RequestController;
import com.rolandVoxes.internetAPIBenchmark.controller.TimerController;
import com.rolandVoxes.internetAPIBenchmark.model.MockModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    @FXML public TabPane appTabPane;

    @FXML public TextField urlRequestText;
    @FXML public TextField countRequestText;
    @FXML public Button startBenchmarkButton;
    @FXML public TextArea resultTextArea;

    @FXML public ListView<String> historyResultsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        if(newValue.getId().equals("historyPane")) refreshHistory();
                    }
                }
        );
    }

    @FXML
    void startBenchmark(ActionEvent action) {
        String urlText = urlRequestText.getText();
        String countText = countRequestText.getText();

        RequestController requestController = new RequestController();
        TimerController timerController = new TimerController();
//        String url = "https://api.chucknorris.io/jokes/random";

        MockModel previousMockModel = JsonController.load("data.json", MockModel.class);
        System.out.println(previousMockModel);

        timerController.startTimer();
        requestController.sendRequest(urlText);
        timerController.stopTimer();
        float duration = timerController.getDuration();
        System.out.println(duration);

        MockModel mockModel = new MockModel(urlText, duration);
        JsonController.save(mockModel, "data.json");

        resultTextArea.setText(String.valueOf(duration));
        System.out.println("Wykonano polaczenie");
    }

    void refreshHistory() {
        historyResultsListView.getItems().clear();
        historyResultsListView.getItems().add("Pierwszy test");
        historyResultsListView.getItems().add("Drugi test");
        historyResultsListView.getItems().add("Trzeci test");
    }
}
