package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.dto.UserDto;
import net.javaguides.sms.entity.User;

public interface UserService {
	List<User> getAllUsers();
	User findByUsername(String username);
	User save(UserDto userDto);
	User saveUser(User user);
	User getUserById(Long id);
	User updateUser(User user);
	void deleteStudentById(Long id);
}
