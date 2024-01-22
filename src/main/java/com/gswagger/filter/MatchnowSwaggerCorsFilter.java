package com.gswagger.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.*;

public class MatchnowSwaggerCorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setCorsResponseHeaders(response);
        filterChain.doFilter(request, response);
    }

    private void setCorsResponseHeaders(HttpServletResponse response) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "*");
    }

    @Override
    public void destroy() {
    }
}
