package com.level4.office.global.image;

import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.image.s3.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final S3Upload s3Upload;

    @Override
    public String uploadImage(MultipartFile imageUrl) throws IOException {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new CustomException(ErrorCode.ELEMENTS_IS_REQUIRED);
        }
        return s3Upload.upload(imageUrl);
    }

    @Override
    public String updateImage(MultipartFile imageUrl, String currentImageUrl) throws IOException {
        return Optional.ofNullable(imageUrl)
                .filter(imageFile -> !imageFile.isEmpty())
                .map(imageFile -> {
                    try {
                        return s3Upload.upload(imageUrl);
                    } catch (IOException e) {
                        throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
                    }
                }).orElse(currentImageUrl);
    }

}
