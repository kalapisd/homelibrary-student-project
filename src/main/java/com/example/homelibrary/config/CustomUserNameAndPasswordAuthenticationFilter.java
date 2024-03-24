package com.example.homelibrary.config;

import com.example.homelibrary.service.MyUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import java.io.IOException;

public class CustomUserNameAndPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final MyUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public CustomUserNameAndPasswordAuthenticationFilter(MyUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        super("/login");
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        this.setSecurityContextRepository(
                new DelegatingSecurityContextRepository(
                        new RequestAttributeSecurityContextRepository(),
                        new HttpSessionSecurityContextRepository()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, ServletException, IOException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            String userName = obtainUserName(request);
            userName = (userName != null) ? userName.trim() : "";
            String password = obtainPassword(request);
            password = (password != null) ? password : "";

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (userDetails.getPassword().equals(password)) {
                Authentication authToken = new UsernamePasswordAuthenticationToken(userName, password);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);


                Authentication auth = authenticationManager.authenticate(authToken);
                this.getSuccessHandler().onAuthenticationSuccess(request, response, auth);
                return auth;
            } else {
                throw new AuthenticationException("Invalid password") {};
            }
        } catch (AuthenticationException e) {
            this.getFailureHandler().onAuthenticationFailure(request, response, e);
        }
        return null;
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getHeader("password");
    }
    protected String obtainUserName(HttpServletRequest request) {
        return request.getHeader("userName");
    }

}
