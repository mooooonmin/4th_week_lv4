package com.level4.office.controller;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.dto.join.UserJoinResponseDto;
import com.level4.office.dto.login.UserLoginRequestDto;
import com.level4.office.dto.login.UserLoginResponseDto;
import com.level4.office.security.UserDetailsServiceImpl;
import com.level4.office.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponseDto> createUser(@Valid @RequestBody UserJoinRequestDto requestDto) {
        UserJoinResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
