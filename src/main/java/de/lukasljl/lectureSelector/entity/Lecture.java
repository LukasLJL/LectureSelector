package de.lukasljl.lectureSelector.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Lecture {
    private String name;
    private String url;
    private String password;
}
