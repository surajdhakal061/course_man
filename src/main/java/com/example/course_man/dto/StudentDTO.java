package com.example.course_man.dto;

import lombok.Data;
import java.util.Set;

@Data
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> courseIds;
}