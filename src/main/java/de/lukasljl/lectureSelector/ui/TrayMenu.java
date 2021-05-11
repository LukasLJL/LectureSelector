package de.lukasljl.lectureSelector.ui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import de.lukasljl.lectureSelector.entity.Lecture;

public class TrayMenu {

    public void generateMenu(ArrayList<Lecture> lectures) {
        //Check if SysTray is Supported
        if (!SystemTray.isSupported()) {
            System.err.println("No SystemTray Support!");
        }

        //Import TrayIcon
        Image image = Toolkit.getDefaultToolkit().getImage(SystemTray.class.getResource("/img/icon.png"));
        //RightClick Menu
        PopupMenu popupMenu = new PopupMenu();


        //Add Image to TrayIcon
        TrayIcon trayIcon = new TrayIcon(image, "LectureManager", popupMenu);
        trayIcon.setImageAutoSize(true);
        //Gen SysTray
        SystemTray systemTray = SystemTray.getSystemTray();


        //Add Lecture Items
        for (Lecture lecture : lectures) {
            MenuItem itemLecture = new MenuItem(lecture.getName());
            //Open URL from Lecture
            itemLecture.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(URI.create(lecture.getUrl()));
                    //Copy Password to Clipboard
                    StringSelection stringSelection = new StringSelection(lecture.getPassword());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, stringSelection);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            popupMenu.add(itemLecture);
        }

        //Basic Items
        //Settings
        MenuItem itemSettings = new MenuItem("Settings");
        itemSettings.addActionListener(e -> {
            Settings settings = new Settings(lectures);
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

    private void addTrayIcon(SystemTray systemTray, TrayIcon trayIcon){
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
