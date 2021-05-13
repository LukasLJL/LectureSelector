package de.lukasljl.lectureSelector.lectureManager;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;

import java.io.File;
import java.util.ArrayList;

public class Manager {

    private final String filePathLectures = System.getProperty("user.home") + "/LectureManager.json";
    private final String filePathSettings = System.getProperty("user.home") + "/LectureManager_Settings.json";

    public Setting loadSettings(){
        File jsonInput = new File(filePathSettings);
        if (!jsonInput.exists()) {
            generateSettingsFile();
        }
        Importer importer = new Importer();
        return importer.loadSettingsFromFile(filePathSettings);
    }

    public ArrayList<Lecture> loadLectures() {
        File jsonInput = new File(filePathLectures);
        if (!jsonInput.exists()) {
            generateSettingsFile();
        }
        Importer importer = new Importer();
        return importer.loadLecturesFromFile(filePathLectures);
    }

    public void saveLectures(ArrayList<Lecture> lectures) {
        Exporter exporter = new Exporter();
        File file = new File(filePathLectures);
        exporter.saveToFile(lectures, filePathLectures);
    }

    public void saveSettings(Setting setting){
        Exporter exporter = new Exporter();
        File file = new File(filePathSettings);
        exporter.saveToFile(setting, filePathSettings);
    }

    private void generateLectureFile() {
        ArrayList<Lecture> lectures = new ArrayList<>();
        Lecture lecture01 = new Lecture("Example Lecture", "https://example.lecture.url", "example.password");
        Lecture lecture02 = new Lecture("Example Lecture New", "https://example.lecture.url.new", "example.password.new");
        lectures.add(lecture01);
        lectures.add(lecture02);
        saveLectures(lectures);
    }

    private void generateSettingsFile(){
        Setting setting = new Setting(false, "https://url.to.caldav.calendar", "caldav-user", "caldav-password");
        saveSettings(setting);
    }

}
