package de.lukasljl.lectureSelector.lectureManager;

import com.amosgross.JCalDavClient;
import com.amosgross.items.JCalDavCalenderItem;
import de.lukasljl.lectureSelector.entity.Lecture;
import de.lukasljl.lectureSelector.entity.Setting;
import de.lukasljl.lectureSelector.ui.TrayMenu;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Notifier {
    private Setting setting;
    private ArrayList<JCalDavCalenderItem> calenderItems;

    public Notifier(Setting setting) {
        this.setting = setting;
    }

    private void getCalendar() {
        JCalDavClient client = new JCalDavClient(setting.getUrl(), setting.getUser(), setting.getPassword());
        this.calenderItems = client.fetchCalendar();
    }

    public void startNotifier(ArrayList<Lecture> lectures, TrayIcon trayIcon) {
        //getting calendar
        getCalendar();
        LocalDateTime currentDateTime = LocalDateTime.now();
        ArrayList<JCalDavCalenderItem> todayLectures = new ArrayList<>();

        for (JCalDavCalenderItem item : this.calenderItems) {
            if (item.getStartTime().isAfter(currentDateTime) && item.getStartTime().isBefore(currentDateTime.plusDays(1))) {
                todayLectures.add(item);
            }
        }

        //Create Timers for today lectures
        for (JCalDavCalenderItem item : todayLectures) {
            Date date = Date.from(item.getStartTime().minusMinutes(5).atZone(ZoneId.systemDefault()).toInstant());
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    trayIcon.displayMessage(item.getSummary(), item.getStartTime().toLocalTime().toString() + item.getDescription(), TrayIcon.MessageType.INFO);
                    for (Lecture lecture : lectures) {
                        if (item.getSummary().contains(lecture.getName())) {
                            trayIcon.addActionListener(e -> new TrayMenu().openLecture(lecture));
                        }
                    }
                }
            }, date);
        }
    }
}
