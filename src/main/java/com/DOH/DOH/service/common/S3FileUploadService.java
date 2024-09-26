package com.DOH.DOH.service.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class S3FileUploadService {

    private final AmazonS3 amazonS3;
    private final String bucketName= "doh-contest-storage";

    public S3FileUploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFileToS3Bucket(MultipartFile file) throws IOException {

//원래 파일이름에 랜덤아이디를 붙여서 파일 이름을 생성함
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 메타데이터 생성 및 설정  (메타데이터(Metadata)는 데이터를 설명하는 데이터, 즉 데이터에 관한 정보)
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize()); // 파일의 크기 설정

        // InputStream으로 파일을 읽어들임
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            amazonS3.putObject(putObjectRequest);
        }
        String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
        log.info("fileUrl 로 파일이 업로드 되었습니다."+fileUrl);
        return fileUrl; // S3 URL 반환
    }
    // 파일 삭제
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
