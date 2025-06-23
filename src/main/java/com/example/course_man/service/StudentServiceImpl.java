package com.example.course_man.service;

import com.example.course_man.dto.StudentDTO;
import com.example.course_man.entity.Course;
import com.example.course_man.entity.Student;
import com.example.course_man.exception.ResourceNotFoundException;
import com.example.course_man.repository.CourseRepository;
import com.example.course_man.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        if (studentDTO.getCourseIds() != null && !studentDTO.getCourseIds().isEmpty()) {
            Set<Course> courses = studentDTO.getCourseIds().stream()
                    .map(courseId -> courseRepository.findById(courseId)
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId)))
                    .collect(Collectors.toSet());
            student.setCourses(courses);
        }
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return toDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = getStudentEntityById(id);
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        if (studentDTO.getCourseIds() != null) {
            Set<Course> courses = studentDTO.getCourseIds().stream()
                    .map(courseId -> courseRepository.findById(courseId)
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId)))
                    .collect(Collectors.toSet());
            student.setCourses(courses);
        }
        Student updated = studentRepository.save(student);
        return toDTO(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentEntityById(id);
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public void assignCourseToStudent(Long studentId, Long courseId) {
        Student student = getStudentEntityById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        student.getCourses().add(course);
        studentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> getStudentCourses(Long studentId) {
        Student student = getStudentEntityById(studentId);
        return student.getCourses();
    }

    private Student getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        if (student.getCourses() != null && !student.getCourses().isEmpty()) {
            dto.setCourseIds(student.getCourses().stream().map(Course::getId).collect(Collectors.toSet()));
        } else {
            dto.setCourseIds(Set.of());
        }
        return dto;
    }
}