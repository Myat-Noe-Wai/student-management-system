package net.javaguides.sms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.ClassSchedule;
import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.ClassScheduleRepository;
import net.javaguides.sms.repository.StudentRepository;

@Service
public class ScheduleService {
	private final ClassScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;

    public ScheduleService(ClassScheduleRepository scheduleRepository, StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
    }

//    public List<ClassSchedule> getUpcomingClasses(String studentUsername) {
//        Student student = studentRepository.findByUsername(studentUsername);
//        List<Long> courseIds = student.getCourses().stream().map(Course::getId).toList();
//
//        return scheduleRepository.findByCourseIdInAndStartTimeAfter(courseIds, LocalDateTime.now());
//    }
    
    public List<ClassSchedule> getUpcomingClasses() {
        return scheduleRepository.findAll();
    }
}
