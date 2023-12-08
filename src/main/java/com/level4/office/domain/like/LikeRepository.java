package com.level4.office.domain.like;

import com.level4.office.domain.review.entity.Review;
import com.level4.office.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndReview(User user, Review review);

    int countByReview(Review review);
}
