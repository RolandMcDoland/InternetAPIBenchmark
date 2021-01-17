package com.rolandVoxes.internetAPIBenchmark.controller;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JsonController {
    public static <T> void save(T object, String fileName) {
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

    public static <T> T load(String fileName, Class<T> clazz) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            String json = "";
            while (fileReader.hasNextLine()) {
                json += fileReader.nextLine();
            }
            fileReader.close();

            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
