package com.level4.office.global.image.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

@Service
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public S3Upload(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public InputStream downloadFile(String key) {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, key));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        return objectInputStream;
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
    public String upload2(String originalFilename, InputStream inputStream) throws IOException {

        String s3FileName = UUID.randomUUID() + "-" + originalFilename;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(inputStream.available());

        amazonS3.putObject(bucket, s3FileName, inputStream, objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void delete(String fileUrl) throws UnsupportedEncodingException {
        fileUrl = URLDecoder.decode(fileUrl, "UTF-8");
        String keyName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucket, keyName);
    }
}