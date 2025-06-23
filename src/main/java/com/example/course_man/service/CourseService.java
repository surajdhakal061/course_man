package com.example.course_man.service;

import com.example.course_man.dto.CourseDTO;
import com.example.course_man.entity.Student;
import java.util.List;
import java.util.Set;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);

    CourseDTO getCourseById(Long id);

    List<CourseDTO> getAllCourses();

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    void deleteCourse(Long id);

    Set<Student> getCourseStudents(Long courseId);
}