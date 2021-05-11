package de.lukasljl.lectureSelector.ui;

import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.lectureManager.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Settings {

    private ArrayList<Lecture> lectures;

    public Settings(ArrayList<Lecture> lectures){
        this.lectures = lectures;
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
        jFrame.setTitle("LectureManger - Settings");
        jFrame.setVisible(true);

        //Create Base JPanel to store every component
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));


        //Lectures Content
        for (Lecture lecture : lectures) {
            jPanel.add(addLecture(lecture, jPanel));
        }

        //Add Field
        JPanel jPanelField = new JPanel();
        jPanelField.setLayout(new BoxLayout(jPanelField, BoxLayout.X_AXIS));

        JButton buttonAddField = new JButton("Add Lecture");
        buttonAddField.addActionListener(e -> {
            Lecture lecture = new Lecture("Example Name", "Example URL", "Example Password");
            lectures.add(lecture);
            jPanel.add(addLecture(lecture, jPanel), jPanel.getComponentCount() - 2);
            SwingUtilities.updateComponentTreeUI(jFrame);
        });

        jPanelField.add(buttonAddField);
        jPanel.add(jPanelField);

        //Buttons
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
            trayMenu.generateMenu(lectures);
        });

        jPanelButtons.add(buttonCancel);
        jPanelButtons.add(buttonSave);

        //Prepare panels
        jPanel.add(jPanelButtons);
        jFrame.add(jPanel);
        jFrame.pack();

        jFrame.setSize(1280, 720);
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
        name.getDocument().addDocumentListener((ChangeDocumentListener) e-> {
            lecture.setName(name.getText());
            lectures.set(lectureIndex, lecture);
        });

        url.getDocument().addDocumentListener((ChangeDocumentListener) e-> {
            lecture.setUrl(url.getText());
            lectures.set(lectureIndex, lecture);
        });

        password.getDocument().addDocumentListener((ChangeDocumentListener) e-> {
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

    private void deleteLecture(Lecture lecture) {
        lectures.remove(lecture);
    }

    private void saveLectures(){
        Manager manager = new Manager();
        manager.saveLectures(lectures);
    }
}
