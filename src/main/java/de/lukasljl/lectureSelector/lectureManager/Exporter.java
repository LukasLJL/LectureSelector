package de.lukasljl.lectureSelector.lectureManager;

import com.google.gson.Gson;
import de.lukasljl.lectureSelector.entity.Lecture;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Exporter {
    public void saveLecturesToFile(ArrayList<Lecture> lectures, String filePath) {
        Gson gson = new Gson();
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(lectures, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
