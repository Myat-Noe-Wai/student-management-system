package net.javaguides.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.javaguides.sms.service.AssignStudentToCourseService;

@Controller
@RequestMapping("/students")
public class AssignStudentToCourseController {
    @Autowired
    private AssignStudentToCourseService studentService;

    @GetMapping("/assign")
    public String showAssignPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", studentService.getAllCourses());
        return "assign-student"; // Thymeleaf template
    }

    @PostMapping("/assign")
    public String assignStudentToCourse(@RequestParam Long studentId, @RequestParam Long courseId, RedirectAttributes redirectAttributes) {
    	boolean isAssigned = studentService.assignStudentToCourse(studentId, courseId);
    	if (isAssigned) {
            redirectAttributes.addFlashAttribute("success", "Assignment successful!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Assignment failed!");
        }
        return "redirect:/students/assign";
    }
}
