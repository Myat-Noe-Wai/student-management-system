package net.javaguides.sms.configuration;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.javaguides.sms.entity.User;

import net.javaguides.sms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

 private UserRepository userRepository;

 public CustomUserDetailsService(UserRepository userRepository) {
  super();
  this.userRepository = userRepository;
 }

// @Override
// public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//  User user = userRepository.findByUsername(username);
//  if (user == null) {
//   throw new UsernameNotFoundException("Username or Password not found");
//  }
//  return new CustomUserDetails(user.getUsername(), user.getPassword(), authorities(), user.getFullname());
// }
//
// public Collection<? extends GrantedAuthority> authorities() {
//  return Arrays.asList(new SimpleGrantedAuthority("USER"));
// }

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

     User user = userRepository.findByUsername(username);
     if (user == null) {
         throw new UsernameNotFoundException("Username or Password not found");
     }

     // Prefix with ROLE_ to match @PreAuthorize("hasRole('STUDENT')")
     SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

     return new CustomUserDetails(user.getUsername(), user.getPassword(),
                                  Arrays.asList(authority), user.getFullname());
 }
	//You don’t need this anymore, or you can make it dynamic if needed
	public Collection<? extends GrantedAuthority> authorities(String role) {
	  return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
	}
}
