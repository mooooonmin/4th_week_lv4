package com.level4.office.entity;

import com.level4.office.dto.join.UserJoinRequestDto;
import com.level4.office.entity.enumType.GenderTypeEnum;
import com.level4.office.entity.enumType.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickName, UserRoleEnum role,
                GenderTypeEnum gender, String phoneNum, String address) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    // DTO를 통해 객체를 생성하는 편의 메소드
    public static User from(UserJoinRequestDto requestDto) {
        return User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickName(requestDto.getNickName())
                .role(UserRoleEnum.USER) // 기본값
                .gender(requestDto.getGender())
                .phoneNum(requestDto.getPhoneNum())
                .address(requestDto.getAddress())
                .build();
    }

}
