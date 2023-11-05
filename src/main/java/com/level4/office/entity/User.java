package com.level4.office.entity;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.entity.enumType.GenderTypeEnum;
import com.level4.office.entity.enumType.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Enumerated(EnumType.STRING)
    private GenderTypeEnum gender;

    private String phoneNum;

    private String address;

    public User(UserJoinRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.nickName = requestDto.getNickName();
        this.address = requestDto.getAddress();
        this.phoneNum = requestDto.getPhoneNum();
        this.gender = requestDto.getGender();
        this.role = requestDto.getRole() != null ? requestDto.getRole() : UserRoleEnum.USER;
    }

}
