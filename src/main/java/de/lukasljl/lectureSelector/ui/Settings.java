package de.lukasljl.lectureSelector.ui;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;
import de.lukasljl.lectureSelector.lectureManager.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Settings {

    private ArrayList<Lecture> lectures;
    private Setting settings;
    private final Dimension spacing = new Dimension(0, 2);

    public Settings(ArrayList<Lecture> lectures, Setting settings) {
        this.lectures = lectures;
        this.settings = settings;
    }

    public JButton getSettings() {
        //Set OS Look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //Create JFrame
        JFrame jFrame = new JFrame();

        //JFrame Settings
        jFrame.setTitle("LectureSelector - Settings");
        Image image = Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/img/icon.png"));
        jFrame.setIconImage(image);
        jFrame.setVisible(true);

        //Create Base JPanel to store every component
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(Box.createRigidArea(this.spacing));

        //Lectures Content
        for (Lecture lecture : lectures) {
            jPanel.add(addLecture(lecture, jPanel));
            jPanel.add(Box.createRigidArea(spacing));
        }

        //Add Field
        addSection(jPanel, jFrame);

        //CalDav Settings Button
        calDavSettingButton(jPanel);

        //Buttons
        JButton buttonSave = settingButtons(jPanel, jFrame);

        //Prepare panels
        jFrame.add(jPanel);
        jFrame.pack();

        jFrame.setSize(1280, 420);
        return buttonSave;
    }

    private JPanel addLecture(Lecture lecture, JPanel jPanel) {
        int lectureIndex = lectures.indexOf(lecture);
        //Panel
        JPanel jPanelLecture = new JPanel();
        jPanelLecture.setLayout(new BoxLayout(jPanelLecture, BoxLayout.X_AXIS));

        //Labels
        JLabel labelName = new JLabel("Name:");
        JLabel labelURL = new JLabel("URL:");
        JLabel labelPassword = new JLabel("Password:");

        //Size Setting
        Dimension dimension = new Dimension(400, 20);

        //TextFields
        JTextField name = new JTextField(lecture.getName(), 1);
        name.setMaximumSize(dimension);
        JTextField url = new JTextField(lecture.getUrl(), 1);
        url.setMaximumSize(dimension);
        JTextField password = new JTextField(lecture.getPassword(), 1);
        password.setMaximumSize(dimension);

        //Listener
        name.getDocument().addDocumentListener((ChangeDocumentListener) e -> {
            lecture.setName(name.getText());
            lectures.set(lectureIndex, lecture);
        });

        url.getDocument().addDocumentListener((ChangeDocumentListener) e -> {
            lecture.setUrl(url.getText());
            lectures.set(lectureIndex, lecture);
        });

        password.getDocument().addDocumentListener((ChangeDocumentListener) e -> {
            lecture.setPassword(password.getText());
            lectures.set(lectureIndex, lecture);
        });

        //Button
        JButton buttonDelete = new JButton("-");
        buttonDelete.addActionListener(e -> {
            deleteLecture(lecture);
            jPanel.remove(jPanelLecture);
            SwingUtilities.updateComponentTreeUI(jPanel);
        });

        //Add Content
        jPanelLecture.add(labelName);
        jPanelLecture.add(name);
        jPanelLecture.add(labelURL);
        jPanelLecture.add(url);
        jPanelLecture.add(labelPassword);
        jPanelLecture.add(password);
        jPanelLecture.add(buttonDelete);
        return jPanelLecture;
    }

    private void addSection(JPanel jPanel, JFrame jFrame) {
        //Panel
        JPanel jPanelField = new JPanel();
        jPanelField.setLayout(new BoxLayout(jPanelField, BoxLayout.X_AXIS));
        //Button
        JButton buttonAddField = new JButton("Add Lecture");
        buttonAddField.addActionListener(e -> {
            Lecture lecture = new Lecture("Example Name", "Example URL", "Example Password");
            this.lectures.add(lecture);
            jPanel.add(addLecture(lecture, jPanel), jPanel.getComponentCount() - 6);
            SwingUtilities.updateComponentTreeUI(jFrame);
        });
        jPanelField.add(buttonAddField);
        jPanel.add(jPanelField);
        jPanel.add(Box.createRigidArea(this.spacing));
    }

    private void calDavSettingButton(JPanel jPanel) {
        JPanel jPanelFieldCalDav = new JPanel();
        jPanelFieldCalDav.setLayout(new BoxLayout(jPanelFieldCalDav, BoxLayout.X_AXIS));
        JButton buttonCaldavSettings = new JButton("CalDav Settings");
        buttonCaldavSettings.addActionListener(e -> {
            CalDavSettings calDavSettings = new CalDavSettings(this.settings);
            calDavSettings.openCalDavSettings();
        });

        jPanelFieldCalDav.add(buttonCaldavSettings);
        jPanel.add(jPanelFieldCalDav);
        jPanel.add(Box.createRigidArea(this.spacing));
    }

    private JButton settingButtons(JPanel jPanel, JFrame jFrame){
        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));

        //Cancel
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> jFrame.dispose());

        //Save
        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            saveLectures();
            jFrame.dispose();
            TrayMenu trayMenu = new TrayMenu();
            trayMenu.generateMenu(lectures, settings);
        });

        jPanelButtons.add(buttonCancel);
        jPanelButtons.add(buttonSave);

        jPanel.add(jPanelButtons);
        jPanel.add(Box.createRigidArea(this.spacing));
        return buttonSave;
    }

    private void deleteLecture(Lecture lecture) {
        lectures.remove(lecture);
    }

    private void saveLectures() {
        Manager manager = new Manager();
        manager.saveLectures(lectures);
    }
}
