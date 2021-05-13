package de.lukasljl.lectureSelector.ui;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;
import de.lukasljl.lectureSelector.lectureManager.Notifier;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class TrayMenu {

    public void generateMenu(ArrayList<Lecture> lectures, Setting lectureSetting) {
        //Check if SysTray is Supported
        if (!SystemTray.isSupported()) {
            System.err.println("No SystemTray Support!");
        }

        //Import TrayIcon
        Image image = Toolkit.getDefaultToolkit().getImage(TrayMenu.class.getResource("/img/icon.png"));
        //RightClick Menu
        PopupMenu popupMenu = new PopupMenu();

        //Add Image to TrayIcon
        TrayIcon trayIcon = new TrayIcon(image, "LectureManager", popupMenu);
        trayIcon.setImageAutoSize(true);
        //Gen SysTray
        SystemTray systemTray = SystemTray.getSystemTray();

        if(lectureSetting.isCalDav()){
            //Adding Notifier to get Notifications for upcoming lectures
            Notifier notifier = new Notifier(lectureSetting);
            notifier.startNotifier(lectures, trayIcon);
        }

        //Add Lecture Items
        for (Lecture lecture : lectures) {
            MenuItem itemLecture = new MenuItem(lecture.getName());
            itemLecture.addActionListener(e -> openLecture(lecture));
            popupMenu.add(itemLecture);
        }

        //Basic Items
        //Settings
        MenuItem itemSettings = new MenuItem("Settings");
        itemSettings.addActionListener(e -> {
            Settings settings = new Settings(lectures, lectureSetting);
            settings.getSettings().addActionListener(e1 -> systemTray.remove(trayIcon));
        });
        popupMenu.add(itemSettings);

        //Exit
        MenuItem itemExit = new MenuItem("Exit");
        itemExit.addActionListener(e -> System.exit(0));
        popupMenu.add(itemExit);

        //Stuff
        addTrayIcon(systemTray, trayIcon);
    }

    private void addTrayIcon(SystemTray systemTray, TrayIcon trayIcon) {
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void openLecture(Lecture lecture) {
        //Open URL from Lecture
        try {
            Desktop.getDesktop().browse(URI.create(lecture.getUrl()));
            //Copy Password to Clipboard
            StringSelection stringSelection = new StringSelection(lecture.getPassword());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
