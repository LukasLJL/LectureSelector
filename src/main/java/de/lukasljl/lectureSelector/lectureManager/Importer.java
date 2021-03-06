package de.lukasljl.lectureSelector.lectureManager;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Importer {

    public ArrayList<Lecture> loadLecturesFromFile(String filePath) {
        Gson gson = new Gson();
        ArrayList<Lecture> lectures = null;
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filePath));
            lectures = new ArrayList<>(Arrays.asList(gson.fromJson(jsonReader, Lecture[].class)));
        } catch (FileNotFoundException e) {
            System.err.println("File not Found!");
        }
        return lectures;
    }

    public Setting loadSettingsFromFile(String filePath){
        Gson gson = new Gson();
        Setting setting = null;
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filePath));
            setting = gson.fromJson(jsonReader, Setting.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return setting;
    }
}
