package de.lukasljl.lectureSelector.lectureManager;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Exporter {

    public void saveToFile(Object objectToSave, String filePath) {
        Gson gson = new Gson();
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(objectToSave, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
