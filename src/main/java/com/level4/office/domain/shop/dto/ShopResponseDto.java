package com.level4.office.domain.shop.dto;

import com.level4.office.domain.review.dto.ReviewResponseDto;
import com.level4.office.domain.shop.entity.Shop;
import com.level4.office.domain.shop.entity.ShopType;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class ShopResponseDto {

    private final Long shopId;
    private final Long userId;
    private final String shopName;
    private final String shopTime;
    private final String shopTel;
    private final String shopAddress;
    private final ShopType shopType;
    private final String shopDescribe;
    private final String imageUrl;
    private final List<ReviewResponseDto> reviews;

    public ShopResponseDto(Long shopId, Long userId, String shopName, String shopTime, String shopTel,
                           String shopAddress, ShopType shopType, String shopDescribe,
                           String imageUrl, List<ReviewResponseDto> reviews) {
        this.shopId = shopId;
        this.userId = userId;
        this.shopName = shopName;
        this.shopTime = shopTime;
        this.shopTel = shopTel;
        this.shopAddress = shopAddress;
        this.shopType = shopType;
        this.shopDescribe = shopDescribe;
        this.imageUrl = imageUrl;
        this.reviews = reviews;
    }

    public ShopResponseDto(Long shopId, Long userId, String shopName, String shopTime, String shopTel,
                           String shopAddress, ShopType shopType, String shopDescribe,
                           String imageUrl) {
        this.shopId = shopId;
        this.userId = userId;
        this.shopName = shopName;
        this.shopTime = shopTime;
        this.shopTel = shopTel;
        this.shopAddress = shopAddress;
        this.shopType = shopType;
        this.shopDescribe = shopDescribe;
        this.imageUrl = imageUrl;
        this.reviews = Collections.emptyList();
    }

    public static ShopResponseDto from(Shop shop, List<ReviewResponseDto> reviews) {
        return ShopResponseDto.builder()
                .shopId(shop.getShopId())
                .userId(shop.getUser().getUserId())
                .shopName(shop.getShopName())
                .shopTime(shop.getShopTime())
                .shopTel(shop.getShopTel())
                .shopAddress(shop.getShopAddress())
                .shopType(shop.getShopType())
                .shopDescribe(shop.getShopDescribe())
                .imageUrl(shop.getImageUrl())
                .reviews(reviews)
                .build();
    }

    public static ShopResponseDto from(Shop shop) {
        return ShopResponseDto.builder()
                .shopId(shop.getShopId())
                .userId(shop.getUser().getUserId())
                .shopName(shop.getShopName())
                .shopTime(shop.getShopTime())
                .shopTel(shop.getShopTel())
                .shopAddress(shop.getShopAddress())
                .shopType(shop.getShopType())
                .shopDescribe(shop.getShopDescribe())
                .imageUrl(shop.getImageUrl())
                .build();
    }
}