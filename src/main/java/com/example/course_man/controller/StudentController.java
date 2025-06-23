package com.example.course_man.controller;

import com.example.course_man.dto.StudentDTO;
import com.example.course_man.entity.Course;
import com.example.course_man.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // relationship stuff

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> assignCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.assignCourseToStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<Set<Course>> getStudentCourses(@PathVariable Long studentId) {
        Set<Course> courses = studentService.getStudentCourses(studentId);
        return ResponseEntity.ok(courses);
    }
}