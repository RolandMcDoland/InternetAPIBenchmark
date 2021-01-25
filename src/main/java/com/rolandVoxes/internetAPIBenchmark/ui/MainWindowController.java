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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    private ArrayList<MockModel> historyResultList;

    @FXML public TabPane appTabPane;

    @FXML public TextField urlRequestText;
    @FXML public TextField countRequestText;
    @FXML public Button startBenchmarkButton;
    @FXML public TextArea resultTextArea;
    @FXML public VBox boxForCurrentChart;

    @FXML public ListView<String> historyResultsListView;
    @FXML public VBox boxForHistoryChart;

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

        ArrayList<Float> durationList = makeRequests(countText, urlText);

        createAndInsertChart(durationList, boxForCurrentChart);

        saveList(mockModelList, urlText, countText, durationList.stream().reduce(0.0f, Float::sum));
    }

    void refreshHistory() {
        historyResultsListView.getItems().clear();
        ArrayList<MockModel> mockModelList;
        try {
            Type listType = new TypeToken<ArrayList<MockModel>>(){}.getType();
            mockModelList = JsonController.load("data.json", listType);
            historyResultList = mockModelList;
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

    ArrayList<Float> makeRequests(String countText, String urlText) {
        RequestController requestController = new RequestController();
        TimerController timerController = new TimerController();

        ArrayList<Float> durationList = new ArrayList<>();
        float durationSum = 0;

        for (int i = 0; i < Integer.parseInt(countText); i++) {
            timerController.startTimer();
            requestController.sendRequest(urlText);
            timerController.stopTimer();
            float duration = timerController.getDuration();
            durationList.add(duration);
            System.out.println(duration);

            resultTextArea.setText(resultTextArea.getText() + "\n" + String.valueOf(duration));
            System.out.println("Wykonano polaczenie");

            durationSum += duration;
        }

        return durationList;
    }

    @FXML
    void historyResultsListViewSelected() {
        int idx = historyResultsListView.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            ArrayList<Float> durationList = historyResultList.get(idx).getAvgDurations();

            createAndInsertChart(durationList, boxForHistoryChart);
        }
    }

    void createAndInsertChart(ArrayList<Float> list, VBox box) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Test number");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Time [s]");
        ScatterChart<Number, Number> historyScatterChart = new ScatterChart<>(xAxis, yAxis);
        historyScatterChart.setLegendVisible(false);
        XYChart.Series series1 = new XYChart.Series();
        for(int i = 0; i < list.size(); i++) {
            series1.getData().add(new XYChart.Data(Integer.valueOf(i+1), list.get(i)));
        }

        historyScatterChart.getData().addAll(series1);
        box.getChildren().clear();
        box.getChildren().add(historyScatterChart);
    }
}
