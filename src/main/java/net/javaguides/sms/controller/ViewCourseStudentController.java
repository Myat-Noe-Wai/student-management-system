package net.javaguides.sms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.CourseRepository;

@Controller
@RequestMapping("/students")
@PreAuthorize("hasRole('ADMIN')")
public class ViewCourseStudentController {
	@Autowired
	private CourseRepository courseRepository;
	
//	@GetMapping("/coursestudents")
//	public String viewCourseStudents() {
//		return "view-course-students";
//	}
	
	@GetMapping("/coursestudents")
	public String viewCourseStudents(@RequestParam(value = "courseId", required = false) Long courseId, Model model) {
	    List<Course> courses = courseRepository.findAll();
	    model.addAttribute("courses", courses);

	    if (courseId != null) {
	        Optional<Course> selectedCourse = courseRepository.findById(courseId);
	        if (selectedCourse.isPresent()) {
	            List<Student> students = selectedCourse.get().getStudents();
	            model.addAttribute("students", students);
	            model.addAttribute("selectedCourseId", courseId);
	        }
	    }

	    return "view-course-students";
	}

}
