package com.example.course_man.dto;

import lombok.Data;
import java.util.Set;

@Data
public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Long> studentIds;
}