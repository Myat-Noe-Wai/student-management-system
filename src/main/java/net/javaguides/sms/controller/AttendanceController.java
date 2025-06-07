package net.javaguides.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.javaguides.sms.entity.Attendance;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.CourseRepository;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/mark/{courseId}")
    public String showAttendanceForm(@PathVariable Long courseId, Model model) {
        List<Student> students = studentRepository.findStudentsByCourseId(courseId);
        model.addAttribute("students", students);
        model.addAttribute("courseId", courseId);
        model.addAttribute("date", LocalDate.now());
        return "attendance-form";
    }

    @PostMapping("/mark/{courseId}")
    public String markAttendance(@RequestParam Long courseId, @RequestParam LocalDate date, @RequestParam List<Long> studentIds) {
        System.out.println("Mark attendance work!");
    	for (Long studentId : studentIds) {
            attendanceService.markAttendance(studentId, courseId, date, true);
        }
        return "redirect:/attendance/view/" + courseId;
    }

    @GetMapping("/view/{courseId}")
    public String viewAttendance(@PathVariable Long courseId, @RequestParam(required = false) LocalDate date, Model model) {
        if (date == null) date = LocalDate.now();
        List<Attendance> attendanceRecords = attendanceService.getAttendanceByCourse(courseId, date);
        model.addAttribute("attendanceRecords", attendanceRecords);
        return "attendance-view";
    }
}

