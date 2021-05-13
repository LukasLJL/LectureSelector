package de.lukasljl.lectureSelector.ui;

import de.lukasljl.lectureSelector.entity.Setting;
import de.lukasljl.lectureSelector.lectureManager.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CalDavSettings {
    private Setting setting;
    private final Dimension spacing = new Dimension(0, 2);

    public CalDavSettings(Setting setting){
        this.setting = setting;
    }

    public void openCalDavSettings() {
        //Create JFrame
        JFrame jFrame = new JFrame();

        //JFrame Settings
        jFrame.setTitle("LectureSelector - CalDav Settings");
        Image image = Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/img/icon.png"));
        jFrame.setIconImage(image);
        jFrame.setVisible(true);

        //BoxLayout Y
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        //Settings
        addContent(jPanel);

        //Buttons
        addButtons(jFrame, jPanel);

        jFrame.add(jPanel);
        jFrame.setSize(500, 160);
    }

    private void addContent(JPanel jPanel) {
        Dimension dimension = new Dimension(400, 20);
        ArrayList<String> content = new ArrayList<>(Arrays.asList("Activate CalDav", "Calendar-URL", "User", "Password"));

        for (String name : content) {
            //Panel
            JPanel jPanelSettings = new JPanel();
            jPanelSettings.setLayout(new GridLayout(1, 0));
            jPanelSettings.setMaximumSize(dimension);
            //Label
            JLabel jLabelSettings = new JLabel(name, JLabel.LEFT);
            jPanelSettings.add(jLabelSettings);
            //TextField
            JTextField textFieldSettings = new JTextField(1);
            textFieldSettings.setMaximumSize(dimension);
            switch (name) {
                case "Activate CalDav":
                    JCheckBox jCheckBoxCalDav = new JCheckBox();
                    jCheckBoxCalDav.setSelected(this.setting.isCalDav());
                    jCheckBoxCalDav.addActionListener(e -> this.setting.setCalDav(jCheckBoxCalDav.isSelected()));
                    jPanelSettings.add(jCheckBoxCalDav);
                    break;
                case "Calendar-URL":
                    textFieldSettings.setText(this.setting.getUrl());
                    textFieldSettings.getDocument().addDocumentListener((ChangeDocumentListener) e -> this.setting.setUrl(textFieldSettings.getText()));
                    jPanelSettings.add(textFieldSettings);
                    break;
                case "User":
                    textFieldSettings.setText(this.setting.getUser());
                    textFieldSettings.getDocument().addDocumentListener((ChangeDocumentListener) e -> this.setting.setUser(textFieldSettings.getText()));
                    jPanelSettings.add(textFieldSettings);
                    break;
                case "Password":
                    textFieldSettings.setText("**********");
                    textFieldSettings.getDocument().addDocumentListener((ChangeDocumentListener) e -> this.setting.setPassword(textFieldSettings.getText()));
                    jPanelSettings.add(textFieldSettings);
                    break;
            }
            jPanel.add(jPanelSettings);
            jPanel.add(Box.createRigidArea(this.spacing));
        }
    }

    private void addButtons(JFrame jFrame, JPanel jPanel) {
        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));

        //Cancel
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> jFrame.dispose());

        //Save
        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            saveSettings();
            jFrame.dispose();
        });

        jPanelButtons.add(buttonCancel);
        jPanelButtons.add(buttonSave);

        //Prepare panels
        jPanel.add(jPanelButtons);
        jPanel.add(Box.createRigidArea(this.spacing));
    }

    private void saveSettings(){
        Manager manager = new Manager();
        manager.saveSettings(this.setting);
    }
}
