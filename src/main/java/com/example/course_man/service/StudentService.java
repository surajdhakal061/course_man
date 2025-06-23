package com.example.course_man.service;

import com.example.course_man.dto.StudentDTO;
import com.example.course_man.entity.Course;
import java.util.List;
import java.util.Set;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents();

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    void deleteStudent(Long id);

    void assignCourseToStudent(Long studentId, Long courseId);

    Set<Course> getStudentCourses(Long studentId);
}