package com.DOH.DOH.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortFolioUploadDTO {
    private int id;
    private String userEmail;
    private String title;
    private MultipartFile personalWork;
    private String personalFilePath;
    private MultipartFile supportedWork;
    private String supportedFilePath;
    private String content;
}
