package com.level4.office.service;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.dto.join.UserJoinResponseDto;
import com.level4.office.entity.enumType.GenderTypeEnum;
import com.level4.office.exception.CustomException;
import com.level4.office.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Value("${admin.key}")
    private String adminKey;

    @Test
    void createUser() {
        // Given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "nickname",
                "test@example.com",
                "Password@123",
                "Address",
                "1234567890",
                GenderTypeEnum.MALE,
                "INVALID_ADMIN_KEY" // 일반 사용자로 가입되는 경우 테스트
        );

        // When
        UserJoinResponseDto responseDto = userService.createUser(requestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals("nickname", responseDto.getNickName());
        assertEquals("test@example.com", responseDto.getEmail());
        assertEquals("AdminKey 불일치로 사용자로 자동 가입되었습니다", responseDto.getMessage());

        // Check if duplication check works
        assertThrows(CustomException.class, () -> userService.createUser(requestDto));

        // Admin case
        UserJoinRequestDto adminRequestDto = new UserJoinRequestDto(
                "admin",
                "admin@example.com",
                "Password@123",
                "Address",
                "1234567890",
                GenderTypeEnum.MALE,
                adminKey  // 관리자로 가입되는 경우 테스트
        );

        UserJoinResponseDto adminResponseDto = userService.createUser(adminRequestDto);

        assertNotNull(adminResponseDto);
        assertEquals("admin", adminResponseDto.getNickName());
        assertEquals("admin@example.com", adminResponseDto.getEmail());
        assertEquals("성공적으로 관리자로 가입되었습니다", adminResponseDto.getMessage());
    }
}


