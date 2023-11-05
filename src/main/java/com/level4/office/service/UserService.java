package com.level4.office.service;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.dto.join.UserJoinResponseDto;
import com.level4.office.entity.User;
import com.level4.office.entity.enumType.UserRoleEnum;
import com.level4.office.exception.CustomException;
import com.level4.office.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // 어드민키 관련
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${admin.key}")
    private String adminKey;

    // 회원가입
    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto requestDto) {

        // 이메일 중복 확인
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException("이미 존재하는 이메일입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickName(requestDto.getNickName())) {
            throw new CustomException("이미 존재하는 닉네임입니다.");
        }

        // 사용자 정보 저장
        User user = requestDto.toEntity();
        String message;

        // 사용자 역할 설정
        if (adminKey.equals(requestDto.getAdminKey())) {
            user.setRole(UserRoleEnum.ADMIN);
            message = "성공적으로 관리자로 가입되었습니다";
        } else {
            user.setRole(UserRoleEnum.USER);
            message = "AdminKey 불일치로 사용자로 자동 가입되었습니다";
        }

        userRepository.save(user);

        // 응답 반환
        return new UserJoinResponseDto(user, message);
    }
}
