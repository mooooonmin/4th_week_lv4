package com.level4.office.domain.review;

import com.level4.office.domain.review.dto.ReviewRequestDto;
import com.level4.office.domain.review.dto.ReviewResponseDto;
import com.level4.office.global.security.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops/{shopId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("shopId") Long shopId,
                                                          @RequestBody ReviewRequestDto requestDto, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId();
        ReviewResponseDto responseDto = reviewService.createReview(userId, shopId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(@PathVariable("shopId") Long shopId) {
        List<ReviewResponseDto> reviews = reviewService.getAllReviews(shopId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("shopId") Long shopId, @PathVariable("reviewId") Long reviewId, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId();
        reviewService.deleteReview(userId, shopId, reviewId);
        return ResponseEntity.noContent().build();
    }
}