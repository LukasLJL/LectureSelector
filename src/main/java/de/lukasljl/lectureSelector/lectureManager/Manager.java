package de.lukasljl.lectureSelector.lectureManager;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.ui.TrayMenu;

import java.io.File;
import java.util.ArrayList;

public class Manager {

    private final String filePath = System.getProperty("user.home") + "\\LectureManager.json";

    public void loadLectures() {
        File jsonInput = new File(filePath);
        if (!jsonInput.exists()) {
            generateFile();
        }
        Importer importer = new Importer();
        ArrayList<Lecture> lectures = importer.loadLecturesFromFile(filePath);
        TrayMenu trayMenu = new TrayMenu();
        trayMenu.generateMenu(lectures);
    }

    public void saveLectures(ArrayList<Lecture> lectures) {
        Exporter exporter = new Exporter();
        File file = new File(filePath);
        exporter.saveLecturesToFile(lectures, filePath);
    }

    private void generateFile() {
        ArrayList<Lecture> lectures = new ArrayList<>();
        Lecture lecture01 = new Lecture("Example Lecture", "https://example.lecture.url", "example.password");
        Lecture lecture02 = new Lecture("Example Lecture New", "https://example.lecture.url.new", "example.password.new");
        lectures.add(lecture01);
        lectures.add(lecture02);
        saveLectures(lectures);
    }

}
