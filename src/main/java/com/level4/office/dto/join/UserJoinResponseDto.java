package com.level4.office.dto.join;

import com.level4.office.entity.User;
import com.level4.office.entity.enumType.GenderTypeEnum;
import com.level4.office.entity.enumType.UserRoleEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinResponseDto {
    private String email;
    private String nickName;
    private GenderTypeEnum gender;
    private String address;
    private UserRoleEnum role;
    private String message;

    public UserJoinResponseDto(User user, String message) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.message = message;
    }
}
