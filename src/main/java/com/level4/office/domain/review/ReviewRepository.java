package com.level4.office.domain.review;

import com.level4.office.domain.review.entity.Review;
import com.level4.office.domain.shop.entity.Shop;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByShop(Shop shop);
    @Query("SELECT r FROM Review r WHERE r.shop.shopId = :shopId")
    List<Review> findByShopId(@Param("shopId") Long shopId);

    Optional<Review> findByReviewIdAndShop(Long reviewId, Shop shop);
}
