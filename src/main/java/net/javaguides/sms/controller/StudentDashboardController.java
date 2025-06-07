package net.javaguides.sms.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.sms.entity.Attendance;
import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.AttendanceService;
import net.javaguides.sms.service.CourseService;
import net.javaguides.sms.service.StudentService;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')") // Only Students
public class StudentDashboardController {	
	private StudentService studentService;
	private CourseService courseService;
	
	@Autowired
    private AttendanceService attendanceService;
	
	@Autowired
    private StudentRepository studentRepository;
	
	public StudentDashboardController(StudentService studentService, CourseService courseService) {
		super();
		this.studentService = studentService;
		this.courseService = courseService;
	}
	
	@GetMapping("/dashboard")
    public String listCourses(Model model) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    System.out.println("Authenticated user: " + auth.getName());
		    System.out.println("Authorities: " + auth.getAuthorities());

        model.addAttribute("courses", courseService.getAllCourses());
        return "student_dashboard";
    }
	
	@GetMapping("/student_attendance_view/{courseId}")
    public String viewAttendance(@PathVariable Long courseId, @RequestParam(required = false) LocalDate date, Model model) {
        if (date == null) date = LocalDate.now();
        List<Attendance> attendanceRecords = attendanceService.getAttendanceByCourse(courseId, date);
        model.addAttribute("attendanceRecords", attendanceRecords);
        return "attendance-student-view";
    }
	
	@GetMapping("/show_student_attendance_form/{courseId}")
    public String showAttendanceForm(@PathVariable Long courseId, Model model) {
        List<Student> students = studentRepository.findStudentsByCourseId(courseId);
        model.addAttribute("students", students);
        model.addAttribute("courseId", courseId);
        model.addAttribute("date", LocalDate.now());
        return "attendance-form";
    }
	
	@PostMapping("/student_mark_attendance/{courseId}")
    public String markAttendance(@RequestParam Long courseId, @RequestParam LocalDate date, @RequestParam List<Long> studentIds) {
        System.out.println("Called markAttendance");
		for (Long studentId : studentIds) {
            attendanceService.markAttendance(studentId, courseId, date, true);
        }
//        return "redirect:/student/student_attendance_view/" + courseId;
		return "redirect:/student/dashboard";
    }
	
	//attendance-summary for all
//	@GetMapping("/attendance-summary")
//	public String attendanceSummary(Model model, Principal principal) {
//		System.out.println("Principal name: " + principal.getName());
//
//	    Student student = studentService.getStudentByUsername(principal.getName());
//	    Map<Course, Long> attendanceCount = attendanceService.getAttendanceCountByCourse(student.getId());
//	    model.addAttribute("attendanceCount", attendanceCount);
//	    return "attendance-summary";
//	}

	//attendance-summary for one month
	@GetMapping("/attendance-summary")
	public String attendanceSummary(@RequestParam(value = "month", required = false) String month,
	                                Model model, Principal principal) {
	    Student student = studentService.getStudentByUsername(principal.getName());

	    // Default to current month if not provided
	    LocalDate startDate;
	    LocalDate endDate;

	    if (month != null && !month.isEmpty()) {
	        YearMonth yearMonth = YearMonth.parse(month); // "2025-05"
	        startDate = yearMonth.atDay(1); // 2025-05-01
	        endDate = yearMonth.atEndOfMonth(); // 2025-05-31
	    } else {
	        YearMonth currentMonth = YearMonth.now();
	        startDate = currentMonth.atDay(1);
	        endDate = currentMonth.atEndOfMonth();
	    }

	    Map<Course, Long> attendanceCount = attendanceService.getAttendanceCountByCourseAndMonth(student.getId(), startDate, endDate);

	    model.addAttribute("name", student.getFirstName() + " " + student.getLastName());
	    model.addAttribute("attendanceCount", attendanceCount);
	    model.addAttribute("selectedMonth", month); // to keep selected value in UI

	    return "attendance-summary";
	}
}

