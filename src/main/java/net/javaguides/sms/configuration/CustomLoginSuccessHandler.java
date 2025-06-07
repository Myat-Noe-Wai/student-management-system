package net.javaguides.sms.configuration;

import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = request.getContextPath();

        for (GrantedAuthority authority : authorities) {
        	System.out.println("Chee role " + authority.getAuthority());
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
            	System.out.println("Student role");
                redirectURL += "/student/dashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
            	System.out.println("Admin role");
                redirectURL += "/students";
                break;
            }
        }
        System.out.println("Final redirectURL = " + redirectURL);

        response.sendRedirect(redirectURL);
    }
}
