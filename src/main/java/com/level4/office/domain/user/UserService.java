package com.level4.office.domain.user;

import com.level4.office.domain.user.dto.SignupRequestDto;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerNewAccount(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        checkIfEmailExist(email);
        User user = User.from(requestDto, password);
        userRepository.save(user);
    }

    private void checkIfEmailExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
