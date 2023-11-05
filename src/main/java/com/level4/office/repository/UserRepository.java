package com.level4.office.repository;

import com.level4.office.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 유저 찾기
    Optional<User> findByNickName(String nickName);
}
