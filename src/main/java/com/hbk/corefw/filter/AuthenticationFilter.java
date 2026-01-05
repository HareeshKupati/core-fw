package com.hbk.corefw.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.dto.UserDetailsDTO;
import com.hbk.corefw.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    public static final String BASIC_ = "Basic ";
    public static final String BEARER_ = "Bearer ";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null && request.getHeader(HttpHeaders.AUTHORIZATION).startsWith(BEARER_)) {
            try {
                String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION).replaceFirst(BEARER_, "");
                String subject = jwtService.getSubject(bearerToken, JwtService.TokenType.ACCESS_TOKEN);
                if (subject != null) {
                    UserDetailsDTO userDetailsDto = (UserDetailsDTO) userDetailsService.loadUserByUsername(subject);
                        if (jwtService.isLatestToken(userDetailsDto, bearerToken)
                                && SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(userDetailsDto, bearerToken, userDetailsDto.getAuthorities()));
                        }
                }
            } catch (Exception e){
                response.getOutputStream().write(objectMapper.writeValueAsString(new Error(null, e.getMessage(), null, null, null)).getBytes());
            }
        }
        filterChain.doFilter(request, response);
    }
}
