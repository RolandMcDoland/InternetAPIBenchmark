package com.rolandVoxes.internetAPIBenchmark.controller;

import org.apache.http.client.fluent.Request;

public class RequestController {
    public void sendRequest(String url) {
        try {
            String response = Request.Get(url).execute().returnContent().asString();
            System.out.println(response);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
