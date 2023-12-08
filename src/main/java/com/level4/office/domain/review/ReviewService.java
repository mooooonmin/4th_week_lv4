package com.level4.office.domain.review;

import com.level4.office.domain.like.LikeRepository;
import com.level4.office.domain.review.dto.ReviewRequestDto;
import com.level4.office.domain.review.dto.ReviewResponseDto;
import com.level4.office.domain.review.entity.Review;
import com.level4.office.domain.shop.ShopRepository;
import com.level4.office.domain.shop.entity.Shop;
import com.level4.office.domain.user.UserRepository;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public ReviewResponseDto createReview(Long userId, Long shopId, ReviewRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Review review = Review.createReview(user, requestDto.getComment(), shop);
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getReviewId(), user.getUserId(), user.getNickname(), review.getComment(), 0, review.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByShop(shop);
        return reviews.stream().map(review -> {
                    int likeCount = likeRepository.countByReview(review);
                    return new ReviewResponseDto(review.getReviewId(), review.getUser().getUserId(), review.getUser().getNickname(), review.getComment(), likeCount, review.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long userId, Long shopId, Long reviewId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Review review = reviewRepository.findByReviewIdAndShop(reviewId, shop)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!review.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        reviewRepository.deleteById(reviewId);
    }
}