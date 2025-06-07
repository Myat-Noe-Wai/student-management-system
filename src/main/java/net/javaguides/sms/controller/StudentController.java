package net.javaguides.sms.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.CourseService;
import net.javaguides.sms.service.StudentService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {	
	private StudentService studentService;
	private CourseService courseService;
	
	public StudentController(StudentService studentService, CourseService courseService) {
		super();
		this.studentService = studentService;
		this.courseService = courseService;
	}
	
	@GetMapping("/students")
	public String listStudents(Model model) {
		System.out.println("endpoint /students");

	    List<Student> students = studentService.getAllStudents();
	    List<Course> courses = courseService.getAllCourses();

	    model.addAttribute("students", students);
	    model.addAttribute("courses", courses);

	    // Ensure at least one course is available for the navbar
	    if (!courses.isEmpty()) {
	        model.addAttribute("course", courses.get(0));  // Set the first course as default
	    }

	    return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		System.out.println("in /students/new");
		Student student = new Student();
		model.addAttribute("student", student);
		System.out.println("in /students/new");
		return "create_student";
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		System.out.println("in students");
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		Student student = studentService.getStudentById(id);
	    if (student.getDateOfBirth() != null) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = student.getDateOfBirth().format(formatter);
	        model.addAttribute("formattedDate", formattedDate);
	    }
		model.addAttribute("student",student);
		System.out.println("Editing Student: " + studentService.getStudentById(id).getDateOfBirth());  // Debugging log
		return "edit_student";
	}
	
	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setDateOfBirth(student.getDateOfBirth());
		existingStudent.setGender(student.getGender());
		existingStudent.setPhNo(student.getPhNo());
		studentService.updateStudent(existingStudent);
		return "redirect:/students";
	}
	
	@GetMapping("/students/{id}")
	public String delteStudent(@PathVariable Long id, Model model) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
}
