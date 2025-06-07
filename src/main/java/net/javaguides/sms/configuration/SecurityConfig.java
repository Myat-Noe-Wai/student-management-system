package net.javaguides.sms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

 @Autowired
 CustomUserDetailsService customUserDetailsService;
 @Autowired
 private CustomLoginSuccessHandler loginSuccessHandler;

 @Bean
 public static PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	 http.csrf().disable()
	    .authorizeHttpRequests()
	    .requestMatchers("/login", "/register", "/home", "/css/**", "/js/**").permitAll()
	    .requestMatchers("/students/**").hasRole("ADMIN")
	    .requestMatchers("/student/**").hasRole("STUDENT")
	    .requestMatchers("/courses/**", "/teachers/**", "/users/**", "/students/assign", 
	    		"/attendance/**", "/attendance/student_view/**").authenticated()
	    .and()
	    .formLogin()
	    .loginPage("/login")
	    .loginProcessingUrl("/login")
	    .successHandler(loginSuccessHandler) //This uses custom login check
//	    .defaultSuccessUrl("/students", true)  // Redirect to /students after successful login
	    .permitAll()
	    .and()
	    .logout()
	    .invalidateHttpSession(true)
	    .clearAuthentication(true)
	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    .logoutSuccessUrl("/login?logout")
	    .permitAll();

  return http.build();

 }

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

 }
}