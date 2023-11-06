package com.level4.office.repository;

import com.level4.office.entity.Instructor;
import com.level4.office.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
