package com.level4.office.global.security.jwt;

import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationEntryPoint(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)  {
        if (request.getAttribute("exception") == null)
            request.setAttribute("exception",new CustomException(ErrorCode.UNEXPECTED_ERROR));
        handlerExceptionResolver.resolveException(request,response,null,(Exception) request.getAttribute("exception"));
    }

}
