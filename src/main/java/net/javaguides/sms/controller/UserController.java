package net.javaguides.sms.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import net.javaguides.sms.dto.UserDto;
import net.javaguides.sms.service.UserService;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.entity.User;

@Controller
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

	 @Autowired
	 private UserDetailsService userDetailsService;
	
	 private UserService userService;
	
	 public UserController(UserService userService) {
	  this.userService = userService;
	 }
	
	 @GetMapping("/home")
	 public String home(Model model, Principal principal) {
	  UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
	  model.addAttribute("userdetail", userDetails);
	  return "home";
	 }
	
	 @GetMapping("/login")
	 public String login(Model model, UserDto userDto) {
	
	  model.addAttribute("user", userDto);
	  return "login";
	 }
	
	 @GetMapping("/register")
	 public String register(Model model, UserDto userDto) {
	  model.addAttribute("user", userDto);
	  return "register";
	 }
	
	 @PostMapping("/register")
	 public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
	  User user = userService.findByUsername(userDto.getUsername());
	  if (user != null) {
	   model.addAttribute("Userexist", user);
	   return "register";
	  }
	  userService.save(userDto);
	  return "redirect:/register?success";
	 }
 
	@PreAuthorize("hasRole('ADMIN')")
 	@GetMapping("/users")
	public String listUsers(Model model) {
	    List<User> users = userService.getAllUsers();
	    model.addAttribute("users", users);

	    return "userlist";
	}
 	
 	@PreAuthorize("hasRole('ADMIN')")
 	@GetMapping("/users/new")
	public String createUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "create_user";
	}
 	
 	@PreAuthorize("hasRole('ADMIN')")
 	@PostMapping("/users")
	public String saveUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/users";
	}
 	
 	@GetMapping("/users/edit/{id}")
 	public String editUserFrom(@PathVariable Long id, Model model){
 		User user = userService.getUserById(id);
 		model.addAttribute("user", user);
 		return "edit_user";
 	}
 	
 	@PostMapping("/users/{id}")
 	public String updateUser(@PathVariable Long id,
			@ModelAttribute("user") User user,
			Model model) {
 		User existingUser = userService.getUserById(id);
 		existingUser.setId(id);
 		existingUser.setFullname(user.getFullname());
 		existingUser.setUsername(user.getUsername());
// 		existingUser.setPassword(user.getPassword());
 		existingUser.setRole(user.getRole());
 		
 		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
 	        existingUser.setPassword(user.getPassword());
 	    }
 		
 		userService.updateUser(existingUser);
 		return "redirect:/users";
 	}
 	
 	@GetMapping("/users/delete/{id}")
	public String deleteStudent(@PathVariable Long id, Model model) {
		userService.deleteStudentById(id);
		return "redirect:/users";
	}
}

