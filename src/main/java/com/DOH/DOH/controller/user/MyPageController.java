package com.DOH.DOH.controller.user;

import com.DOH.DOH.dto.user.*;
import com.DOH.DOH.mapper.user.MyPageMapper;
import com.DOH.DOH.mapper.user.MyPageProfileMapper;
import com.DOH.DOH.mapper.user.PortFolioUploadMapper;
import com.DOH.DOH.service.user.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class MyPageController {
    private final MyPageProfileMapper myPageProfileMapper;
    private final MyPageProfileService service;
    private final MyPageService myPageService;
    private final PortFolioUploadMapper portFolioUploadMapper;
    private final PortFolioUploadService portFolioUploadService;
    private final AmazonS3 amazonS3;
    private final String bucketName= "doh-contest-storage";
    private final UserSessionService userSessionService;
    private final MyPageMapper myPageMapper;
    private final RegisterService registerService;

    public MyPageController(MyPageProfileMapper myPageProfileMapper, MyPageProfileService service, MyPageService myPageService, PortFolioUploadMapper portFolioUploadMapper, PortFolioUploadService portFolioUploadService, AmazonS3 amazonS3, UserSessionService userSessionService, MyPageMapper myPageMapper, RegisterService registerService) {
        this.myPageProfileMapper = myPageProfileMapper;
        this.service = service;
        this.myPageService = myPageService;
        this.portFolioUploadMapper = portFolioUploadMapper;
        this.portFolioUploadService = portFolioUploadService;
        this.amazonS3 = amazonS3;

        this.userSessionService = userSessionService;
        this.myPageMapper = myPageMapper;
        this.registerService = registerService;
    }

    @GetMapping("/users/mypage")
    public String myPage(Model model, MyPageDTO myPageDTO, MyPageProfileDTO profile, RegisterDTO registerDTO) {

        // 유저 이메일 가져오기
        String userEmail = userSessionService.userEmail();

        profile = service.findByUserEmail(userEmail);

        // myPageDTO 가져오기
        myPageDTO = myPageService.findByuserEmail(userEmail);
        if (myPageDTO == null) {
            myPageMapper.insertUserEmail(userEmail);
        }

        model.addAttribute("registerDTO", registerDTO);
        model.addAttribute("myPageDTO", myPageDTO);
        model.addAttribute("profile", profile);

        return "user/MyPage";
    }

    @GetMapping("/users/mypage/portfolio")
    public String getPortfolioFragment(Model model, MyPageProfileDTO profile) {
        String userEmail = userSessionService.userEmail();
        profile = service.findByUserEmail(userEmail);
        model.addAttribute("profile", profile);
        return "user/MyPagePortFolio";
   }
    private String uploadFileToS3Bucket(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 메타데이터 생성 및 설정
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
   @GetMapping("/users/mypage/upload")
    public String uploadFragment(Model model, PortFolioUploadDTO uploadDTO, MyPageProfileDTO profile) {
       String userEmail = userSessionService.userEmail();
       uploadDTO = portFolioUploadService.findByUserEmail(userEmail);

       model.addAttribute("uploadDTO", uploadDTO);
       return "user/MyPageUpload";
   }
    @PostMapping("/users/mypage/upload")
    public String personalUpload(@RequestParam("personalWork") MultipartFile file, Model model, PortFolioUploadDTO uploadDTO) throws IOException {
        String userEmail = userSessionService.userEmail();

        uploadDTO = portFolioUploadService.findByUserEmail(userEmail);
        if (uploadDTO == null) {
            uploadDTO = new PortFolioUploadDTO();
            uploadDTO.setUserEmail(userEmail);
        }

        if (file != null && !file.isEmpty()) {
            String newFileName = uploadFileToS3Bucket(file);
            uploadDTO.setPersonalFilePath(newFileName);
        }

        portFolioUploadService.insert(uploadDTO);
        return "redirect:/users/mypage/portfolio";
    }

    @GetMapping("/users/mypage/profile/edit/{id}")
    public String geteditProfile(@PathVariable("id") Long id, Model model, @ModelAttribute MyPageProfileDTO profile, RegisterDTO registerDTO, MyPageSkillDTO myPageSkillDTO) {
        String userEmail = userSessionService.userEmail();
        profile = service.findIdByUserEmail(id, userEmail);
        model.addAttribute("registerDTO", registerDTO);
        model.addAttribute("profile", profile);
        model.addAttribute("myPageSkillDTO", myPageSkillDTO);
        return "user/MyPageProfileEdit";
    }
    @PutMapping("/users/mypage/profile/edit/{id}")
    public String editProfile(@PathVariable("id") Long id, Model model,@ModelAttribute MyPageProfileDTO profile,RegisterDTO registerDTO, MyPageSkillDTO myPageSkillDTO) {
        String userEmail = userSessionService.userEmail();
        // 문제 해결: 기존 프로필 데이터를 덮어쓰지 않고, 필요한 필드만 갱신
        MyPageProfileDTO existingProfile = service.findIdByUserEmail(id, userEmail);
        if (existingProfile == null) {
            // 기존 데이터가 없을 경우 에러 처리
            log.error("Profile not found for id: " + id);
            return "redirect:/users/mypage"; // 리디렉션 경로는 상황에 맞게 조정
        }

        profile.setUserEmail(userEmail);
        service.update(profile);
        System.out.println(profile);
        System.out.println(profile);
        System.out.println(profile);
        log.info("editprofile :{}", profile);

        return "redirect:/users/mypage";
    }
}