package com.level4.office.domain.user.controller;

import com.level4.office.domain.user.dto.SignupRequestDto;
import com.level4.office.domain.user.service.UserService;
import com.level4.office.global.dto.SuccessMessageDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SuccessMessageDto UserSignup(@Valid @RequestBody SignupRequestDto signupRequestDto, HttpServletResponse response) {
        userService.signup(signupRequestDto, response);
        return new SuccessMessageDto("회원가입에 성공하였습니다!");
    }

}
