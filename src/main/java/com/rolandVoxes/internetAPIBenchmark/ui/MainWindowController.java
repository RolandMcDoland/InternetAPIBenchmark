package com.rolandVoxes.internetAPIBenchmark.ui;

import com.google.gson.reflect.TypeToken;
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

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
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
        resultTextArea.setText("");
        String urlText = urlRequestText.getText();
        String countText = countRequestText.getText();

//      String url = "https://api.chucknorris.io/jokes/random";
        ArrayList<MockModel> mockModelList = loadList();
        System.out.println(mockModelList);

        float durationSum = makeRequests(countText, urlText);

        saveList(mockModelList, urlText, countText, durationSum);
    }

    void refreshHistory() {
        historyResultsListView.getItems().clear();
        ArrayList<MockModel> mockModelList;
        try {
            Type listType = new TypeToken<ArrayList<MockModel>>(){}.getType();
            mockModelList = JsonController.load("data.json", listType);
            System.out.println(mockModelList);
        } catch(Exception e) {
            return;
        }
        for (int i = 0; i < mockModelList.size(); i++ ) {
            historyResultsListView.getItems().add(mockModelList.get(i).getUrl());
        }
    }

    ArrayList<MockModel> loadList() {
        try {
            Type listType = new TypeToken<ArrayList<MockModel>>(){}.getType();
            return JsonController.load("data.json", listType);
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    void saveList(ArrayList<MockModel> mockModelList, String urlText, String countText, float durationSum) {
        boolean itemFoundFlag = false;
        for (int i = 0; i < mockModelList.size(); i++ ) {
            if(mockModelList.get(i).getUrl().equals(urlText)) {
                mockModelList.get(i).addAvgDuration(durationSum/Integer.parseInt(countText));
                itemFoundFlag = true;
                break;
            }
        }

        if(!itemFoundFlag) {
            MockModel mockModel = new MockModel(urlText);
            mockModel.addAvgDuration(durationSum / Integer.parseInt(countText));
            mockModelList.add(mockModel);
        }

        JsonController.save(mockModelList, "data.json");
    }

    float makeRequests(String countText, String urlText) {
        RequestController requestController = new RequestController();
        TimerController timerController = new TimerController();

        float durationSum = 0;

        for (int i = 0; i < Integer.parseInt(countText); i++) {
            timerController.startTimer();
            requestController.sendRequest(urlText);
            timerController.stopTimer();
            float duration = timerController.getDuration();
            System.out.println(duration);

            resultTextArea.setText(resultTextArea.getText() + "\n" + String.valueOf(duration));
            System.out.println("Wykonano polaczenie");

            durationSum += duration;
        }

        return durationSum;
    }
}
