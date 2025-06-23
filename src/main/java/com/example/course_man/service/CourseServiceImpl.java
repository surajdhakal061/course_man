package com.example.course_man.service;

import com.example.course_man.dto.CourseDTO;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        if (courseDTO.getStudentIds() != null && !courseDTO.getStudentIds().isEmpty()) {
            Set<Student> students = courseDTO.getStudentIds().stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Student not found with id: " + studentId)))
                    .collect(Collectors.toSet());
            course.setStudents(students);
        }
        Course saved = courseRepository.save(course);
        return toDTO(saved);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return toDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = getCourseByIdEntity(id);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        if (courseDTO.getStudentIds() != null) {
            Set<Student> students = courseDTO.getStudentIds().stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Student not found with id: " + studentId)))
                    .collect(Collectors.toSet());
            course.setStudents(students);
        }
        Course updated = courseRepository.save(course);
        return toDTO(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseByIdEntity(id);
        // gotta break the relationship before deleting a course, otherwise things get
        // weird
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }
        courseRepository.delete(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> getCourseStudents(Long courseId) {
        Course course = getCourseByIdEntity(courseId);
        return course.getStudents();
    }

    private Course getCourseByIdEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        if (course.getStudents() != null) {
            dto.setStudentIds(course.getStudents().stream().map(Student::getId).collect(Collectors.toSet()));
        }
        return dto;
    }
}