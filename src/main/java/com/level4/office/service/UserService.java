package com.level4.office.service;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.dto.join.UserJoinResponseDto;
import com.level4.office.dto.login.UserLoginRequestDto;
import com.level4.office.dto.login.UserLoginResponseDto;
import com.level4.office.entity.User;
import com.level4.office.entity.enumType.UserRoleEnum;
import com.level4.office.exception.CustomException;
import com.level4.office.exception.ErrorMessage;
import com.level4.office.jwt.JwtUtil;
import com.level4.office.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // 어드민키 관련
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret.key}")
    private String secretKey;

    // 회원가입
    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto requestDto) {

        // 이메일 중복 확인
        String email = requestDto.getEmail();
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS.getMessage());
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickName(requestDto.getNickName())) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS.getMessage());
        }

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 역할 설정
        UserRoleEnum role;
        String message;
        if (secretKey.equals(requestDto.getAdminKey())) {
            role = UserRoleEnum.ADMIN;
            message = "성공적으로 관리자로 가입되었습니다";
        } else {
            role = UserRoleEnum.USER;
            message = "AdminKey 불일치로 사용자로 자동 가입되었습니다";
        }

        // 사용자 등록
        User user = User.builder()
                .address(requestDto.getAddress())
                .email(email)
                .gender(requestDto.getGender())
                .nickName(requestDto.getNickName())
                .password(password) // 인코딩된 비밀번호 설정
                .phoneNum(requestDto.getPhoneNum())
                .role(role) // 역할 설정
                .build();

        userRepository.save(user);

        // 응답 반환
        return new UserJoinResponseDto(user, message);
    }

    // 로그인
    // 이메일 입력받아서 -> 사용자 조회 -> 비밀번호 검증 -> jwt 토큰 생성 -> 응답 반환
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        // 사용자 조회
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(CustomException.UserNotFoundException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {

            log.error("비밀번호 불일치: {}", requestDto.getEmail());

            throw new CustomException.InvalidPasswordException();
        }

        log.info("이메일에 맞는 비밀번호 확인: {}", requestDto.getEmail());

        // 토큰 생성
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        log.info("사용자를 위한 토큰생성: {}", requestDto.getEmail());

        // 응답 객체 생성 및 반환
        return new UserLoginResponseDto(user, "로그인 성공", token);
    }
}
