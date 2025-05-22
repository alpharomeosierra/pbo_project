package com.example.crud.security;

import com.example.crud.Model.User;
import com.example.crud.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        // fetch your User entity by username
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // redirect based on role (0=user, 1=admin)
        if (user.getRole() == 1) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/");
        }
    }
}
