package com.rolandVoxes.internetAPIBenchmark.controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.util.Map;

public class RequestController {
    public int sendRequest(String url, Map<String, String> headers) {
        Request request = Request.Get(url);
        for (String name : headers.keySet()) {
            request.addHeader(name, headers.get(name));
        }

        try {
            Response response = request.execute();
            return response.returnResponse().getStatusLine().getStatusCode();
        } catch (Exception  e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int sendRequest(String url, Map<String, String> headers, String body) {
        Request request = Request.Post(url).bodyString(body, ContentType.APPLICATION_JSON);
        for (String name : headers.keySet()) {
            request.addHeader(name, headers.get(name));
        }

        try {
            Response response = request.execute();
            return response.returnResponse().getStatusLine().getStatusCode();
        } catch (Exception  e) {
            e.printStackTrace();
        }

        return -1;
    }
}
