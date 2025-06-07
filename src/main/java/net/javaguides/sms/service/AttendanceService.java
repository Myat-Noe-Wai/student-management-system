package net.javaguides.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Attendance;
import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.AttendanceRepository;
import net.javaguides.sms.repository.CourseRepository;
import net.javaguides.sms.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void markAttendance(Long studentId, Long courseId, LocalDate date, boolean present) {
    	Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
    	
    	Attendance attendance = new Attendance();
        attendance.setStudent(studentOpt.get());
        attendance.setCourse(courseOpt.get());
        attendance.setDate(date);
        attendance.setPresent(present);
        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByCourse(Long courseId, LocalDate date) {
        return attendanceRepository.findByCourseIdAndDate(courseId, date);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
    
    //attendance-summary for all
//    public Map<Course, Long> getAttendanceCountByCourse(Long studentId) {
//        List<Attendance> records = attendanceRepository.findByStudentId(studentId);
//        return records.stream()
//            .collect(Collectors.groupingBy(Attendance::getCourse, Collectors.counting()));
//    }

    //attendance-summary for one month
    public Map<Course, Long> getAttendanceCountByCourseAndMonth(Long studentId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> records = attendanceRepository.findByStudentIdAndDateBetweenAndPresentTrue(studentId, startDate, endDate);

        return records.stream()
                .collect(Collectors.groupingBy(Attendance::getCourse, Collectors.counting()));
    }

}

