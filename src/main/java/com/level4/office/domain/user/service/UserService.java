package com.level4.office.domain.user.service;

import com.level4.office.domain.user.dto.SignupRequestDto;
import com.level4.office.domain.user.entity.Gender;
import com.level4.office.domain.user.entity.User;
import com.level4.office.domain.user.entity.UserRoleEnum;
import com.level4.office.domain.user.repository.UserRepository;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto, HttpServletResponse response) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        Gender gender = Gender.valueOf(signupRequestDto.getGender());
        String phoneNumber = signupRequestDto.getPhoneNumber();
        String address = signupRequestDto.getAddress();

        /*유효성 검사*/
        validateEmail(email);
        validatePhoneNumber(phoneNumber);

        User saveUser = new User(email, password, gender, phoneNumber, address, UserRoleEnum.USER);
        userRepository.save(saveUser);
    }

    private void validateEmail(String email) {
        Optional<User> checkUserEmail = userRepository.findByEmail(email);
        if (checkUserEmail.isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        Optional<User> checkPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if(checkPhoneNumber.isPresent()) {
            throw new CustomException(ErrorCode.PHONENUMBER_ALREADY_EXISTS);
        }
    }

}
