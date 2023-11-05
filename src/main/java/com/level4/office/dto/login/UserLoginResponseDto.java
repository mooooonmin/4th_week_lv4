package com.level4.office.dto.login;

import com.level4.office.entity.User;
import com.level4.office.entity.enumType.GenderTypeEnum;
import com.level4.office.entity.enumType.UserRoleEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginResponseDto {

    private String email;
    private String nickName;
    private GenderTypeEnum gender;
    private String address;
    private UserRoleEnum role;
    private String message;
    private String token;

    public UserLoginResponseDto(User user, String message, String token) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.message = message;
        this.token = token;
    }
}