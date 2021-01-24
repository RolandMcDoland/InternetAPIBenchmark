package com.rolandVoxes.internetAPIBenchmark.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonController {
    public static <T> void save(ArrayList<T> object, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ArrayList<T> load(String fileName, Type listType) throws  Exception {
        File file = new File(fileName);
        Scanner fileReader = new Scanner(file);
        String json = "";
        while (fileReader.hasNextLine()) {
            json += fileReader.nextLine();
        }
        fileReader.close();

        Gson gson = new Gson();
        return gson.fromJson(json, listType);
    }
}
