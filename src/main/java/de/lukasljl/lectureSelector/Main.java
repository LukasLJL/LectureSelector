package de.lukasljl.lectureSelector;

import de.lukasljl.lectureSelector.lectureManager.Manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.loadLectures();
    }
}
