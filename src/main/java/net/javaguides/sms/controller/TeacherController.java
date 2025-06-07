package net.javaguides.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Teacher;
import net.javaguides.sms.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teacherlist";
    }
	
	@GetMapping("/create_teacher") //to show the form
    public String showAddTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "create_teacher";
    }
	
	@PostMapping
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
		teacherService.saveTeacher(teacher);
        return "redirect:/teachers";
    }
	
	@GetMapping("/edit/{id}")
    public String showEditTeacherForm(@PathVariable Long id, Model model) {
        model.addAttribute("teacher", teacherService.getTeacherById(id));
        return "edit_teacher"; //html file
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute("teacher") Teacher teacher) {
    	teacher.setId(id);
    	teacherService.saveTeacher(teacher);
        return "redirect:/teachers"; //API endpoint
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
