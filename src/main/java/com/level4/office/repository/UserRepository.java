package com.level4.office.repository;

import com.level4.office.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 유저 찾기
    Optional<User> findByNickName(String nickName);

    // 이메일로 찾기
    Optional<User> findByEmail(String email);

    // 이메일 중복확인
    boolean existsByEmail(String email);

    // 닉네임 중복확인
    boolean existsByNickName(String nickName);
}
