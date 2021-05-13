# LectureSelector
A little tool to Select your Online-Lecture or get a Notification if one starts in the next 5 Minutes.

# Requirements
- JRE 11
- Lectures ;)

# Usage
You can simply start the jar via terminal, after that you will have a little SystemTray Icon with Sample Lectures. <br>
If you want to change them you can open the Settings. <br>
Optional you can add a CalDav Account (testes with NextCloud) to get System Notifications 5 minutes before the Lecture starts.
But this Feature is just working if you name your Lectures the same like in your calendar.<br>
All Settings are stored in your home directory if you want to share them you can just send them to a friend and reuse the config.<br>
BUT It´s not recommended to share the CalDav Settings.

# Dev
## Used Dependencies
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [GSON](https://mvnrepository.com/artifact/com.google.code.gson/gson)
- [JCalDav](https://github.com/grossamos/JCalDav)

## Build
If you want to build the maven project on your own, you can simply run: <br>
``mvn clean compile assembly:single``
