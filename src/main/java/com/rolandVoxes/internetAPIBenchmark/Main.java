package com.rolandVoxes.internetAPIBenchmark;

import com.rolandVoxes.internetAPIBenchmark.controller.JsonController;
import com.rolandVoxes.internetAPIBenchmark.controller.RequestController;
import com.rolandVoxes.internetAPIBenchmark.controller.TimerController;
import com.rolandVoxes.internetAPIBenchmark.model.MockModel;

public class Main {
    public static void main(String[] args) {
        RequestController requestController = new RequestController();
        TimerController timerController = new TimerController();
        String url = "https://api.chucknorris.io/jokes/random";

        MockModel previousMockModel = JsonController.load("data.json", MockModel.class);
        System.out.println(previousMockModel);

        timerController.startTimer();
        requestController.sendRequest(url);
        timerController.stopTimer();
        float duration = timerController.getDuration();
        System.out.println(duration);

        MockModel mockModel = new MockModel(url, duration);
        JsonController.save(mockModel, "data.json");
    }
}
