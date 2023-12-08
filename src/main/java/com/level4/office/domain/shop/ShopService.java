package com.level4.office.domain.shop;

import com.level4.office.domain.review.ReviewRepository;
import com.level4.office.domain.review.dto.ReviewResponseDto;
import com.level4.office.domain.shop.dto.ShopRequestDto;
import com.level4.office.domain.shop.dto.ShopResponseDto;
import com.level4.office.domain.shop.entity.Shop;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.dto.ApiResponseDto;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository;
    private final ImageService imageService;

    // 마이페이지 가게 조회
    public ApiResponseDto<List<ShopResponseDto>> getShopsByUserId(Long userId) {
        List<Shop> shops = shopRepository.findByUser_UserId(userId);

        String message = shops.isEmpty() ? "등록된 가게가 없습니다" : "가게 목록 조회 성공";
        List<ShopResponseDto> shopDtos = shops.stream()
                .map(ShopResponseDto::from)
                .collect(Collectors.toList());

        return new ApiResponseDto<>(message, shopDtos);
    }

    // 가게 등록
    @Transactional
    public ShopResponseDto createShop(ShopRequestDto shopRequestDto, MultipartFile imageUrl,
                                      User currentUser) throws IOException {
        String image = imageService.uploadImage(imageUrl);
        Shop shop = Shop.of(shopRequestDto, image, currentUser);
        shopRepository.save(shop);
        return ShopResponseDto.from(shop);
    }

    // 가게 상세 조회
    @Transactional(readOnly = true)
    public ShopResponseDto getShopDetails(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOP_NOT_FOUND));

        List<ReviewResponseDto> reviews = reviewRepository.findByShopId(shopId).stream()
                .map(review -> ReviewResponseDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUser().getUserId())
                        .nickname(review.getUser().getNickname())
                        .comment(review.getComment())
                        .likeCount(review.getLikeCount())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        if (reviews.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_YET_COMMENT);
        }

        return ShopResponseDto.from(shop, reviews);
    }

    // 가게 전체 조회
    @Transactional(readOnly = true)
    public List<ShopResponseDto> getAllShops() {
        return shopRepository.findAll().stream()
                .map(ShopResponseDto::from).collect(Collectors.toList());
    }

    // 가게 수정
    @Transactional
    public ShopResponseDto updateShop(Long shopId,
                                      ShopRequestDto shopRequestDto,
                                      MultipartFile imageUrl, User currentUser) throws IOException {

        Shop shop = shopRepository.findById(shopId).orElseThrow(
                () -> new CustomException(ErrorCode.SHOP_NOT_FOUND));

        String image = imageService.updateImage(imageUrl, shop.getImageUrl());

        shop.updateShopDetails(
                shopRequestDto.getShopName(),
                shopRequestDto.getShopTime(),
                shopRequestDto.getShopTel(),
                shopRequestDto.getShopType(),
                shopRequestDto.getShopAddress(),
                shopRequestDto.getShopDescribe(),
                image,currentUser);

        return ShopResponseDto.from(shop);
    }

}
