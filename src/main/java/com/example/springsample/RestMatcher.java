package com.example.springsample;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestMatcher implements RequestMatcher {
    private AntPathRequestMatcher matcher;

    public RestMatcher(String url) {
        super();
        matcher = new AntPathRequestMatcher(url);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            return false;
        }

        if (matcher.matches(request)) {
            return false;
        }

        return true;
    }
}
