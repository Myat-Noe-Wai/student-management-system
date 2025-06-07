package net.javaguides.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.dto.UserDto;
import net.javaguides.sms.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 User findByUsername(String username);

	 User save(UserDto userDto);
	}
