package com.level4.office.service;

import com.level4.office.controller.UserController;
import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.entity.User;
import com.level4.office.entity.enumType.GenderTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        String email = "test@example.com";
        String password = "ValidPassword1!";

        // UserJoinRequestDto 객체 생성
        UserJoinRequestDto requestDto = new UserJoinRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);

        // userService를 이용하여 사용자를 생성하고 저장
        userService.createUser(requestDto);
    }

    @Test
    @DisplayName("토큰 여부")
    public void loginWithValidCredentialsShouldReturnToken() throws Exception {
        String loginRequestJson = "{\"email\":\"test@example.com\", \"password\":\"ValidPassword1!\"}";

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("잘못된 자격")
    public void loginWithInvalidCredentialsShouldReturnUnauthorized() throws Exception {
        String loginRequestJson = "{\"email\":\"test@example.com\", \"password\":\"ValidPassword1!\"}";

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("비밀번호 누락")
    public void loginWithMissingCredentialsShouldReturnBadRequest() throws Exception {
        String loginRequestJson = "{\"email\":\"test@example.com\"}";

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
