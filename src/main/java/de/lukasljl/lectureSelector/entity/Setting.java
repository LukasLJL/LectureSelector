package de.lukasljl.lectureSelector.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Setting {
    private boolean calDav;
    private String url;
    private String user;
    private String password;
}
