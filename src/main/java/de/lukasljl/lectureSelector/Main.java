package de.lukasljl.lectureSelector;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;
import de.lukasljl.lectureSelector.lectureManager.Manager;
import de.lukasljl.lectureSelector.ui.TrayMenu;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        ArrayList<Lecture> lectures = manager.loadLectures();
        Setting setting = manager.loadSettings();

        TrayMenu trayMenu = new TrayMenu();
        trayMenu.generateMenu(lectures, setting);
    }
}
