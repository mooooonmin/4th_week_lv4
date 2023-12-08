package com.level4.office.domain.like;

import com.level4.office.domain.review.ReviewRepository;
import com.level4.office.domain.review.entity.Review;
import com.level4.office.domain.user.UserRepository;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository reviewLikeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createLike(Long userId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        reviewLikeRepository.findByUserAndReview(user, review)
                .ifPresent(like -> { throw new CustomException(ErrorCode.ALREADY_LIKED); });

        reviewLikeRepository.save(new Like(user, review));
    }

    @Transactional
    public void deleteLike(Long userId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Like like = reviewLikeRepository.findByUserAndReview(user, review)
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        reviewLikeRepository.delete(like);
    }
}
