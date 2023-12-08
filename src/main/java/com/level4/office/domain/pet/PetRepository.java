package com.level4.office.domain.pet;

import com.level4.office.domain.pet.entity.Pet;
import com.level4.office.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUser(User user);
}
