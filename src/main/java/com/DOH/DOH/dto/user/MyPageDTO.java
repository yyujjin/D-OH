package com.DOH.DOH.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {
    private Long id;
    private String userEmail;
    private MultipartFile profilePhoto;
    private String profilePhotoPath;
}
