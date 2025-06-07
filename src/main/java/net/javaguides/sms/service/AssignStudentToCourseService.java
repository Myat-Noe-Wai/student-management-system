package net.javaguides.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.CourseRepository;
import net.javaguides.sms.repository.StudentRepository;

@Service
public class AssignStudentToCourseService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public boolean assignStudentToCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
            Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

            student.getCourses().add(course);
            course.getStudents().add(student);
            studentRepository.save(student);
            courseRepository.save(course);

            System.out.println("Assigned Student " + student.getFirstName() + " to Course " + course.getName());
            return true;
        } catch (Exception e) {
            System.err.println("Assignment failed: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
