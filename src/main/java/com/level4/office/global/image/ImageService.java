package com.level4.office.global.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile imageUrl) throws IOException;
    String updateImage(MultipartFile imageUrl, String currentImageUrl) throws IOException;

}
