package com.level4.office.global.security.filter;

import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.security.impl.UserDetailsServiceImpl;
import com.level4.office.global.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (req.getHeader(JwtUtil.ACCESSTOKEN_HEADER) == null)
                throw new CustomException(ErrorCode.NOT_FOUND_TOKEN);
            String token = URLDecoder.decode(req.getHeader(JwtUtil.ACCESSTOKEN_HEADER), "UTF-8");

            if (StringUtils.hasText(token) && token != null) {

                String tokenValue = jwtUtil.substringToken(token);
                logger.error("토큰 확인용 : " + tokenValue);
                jwtUtil.validateToken(tokenValue);

                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    logger.error("-->인증오류<--");
                }
            }
        } catch (Exception e) {
            req.setAttribute("exception", e);
        }
        logger.error(req.getRequestURI());
        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = null;
        try {
            authentication = createAuthentication(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
