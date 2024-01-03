package com.level4.office.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level4.office.domain.user.dto.LoginRequestDto;
import com.level4.office.domain.user.entity.UserRoleEnum;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.dto.SuccessMessageDto;
import com.level4.office.global.security.impl.UserDetailsImpl;
import com.level4.office.global.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)throws IOException {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createAccessToken(email,role);
        String refreshToken = jwtUtil.createRefreshToken(email);

        jwtUtil.addJwtToHeader(JwtUtil.ACCESSTOKEN_HEADER,accessToken,response);
        jwtUtil.addJwtToHeader(JwtUtil.REFRESHTOKEN_HEADER,refreshToken,response);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(new SuccessMessageDto("로그인 성공"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(userJson);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(400);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ErrorCode.INVALID_EMAIL_PASSWORD.getMessage());
    }

}
