package com.DOH.DOH.controller.user;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.user.MyPageDTO;
import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.PortFolioUploadDTO;
import com.DOH.DOH.mapper.user.MyPageMapper;
import com.DOH.DOH.mapper.user.MyPageProfileMapper;
import com.DOH.DOH.mapper.user.PortFolioUploadMapper;
import com.DOH.DOH.service.contest.ContestUploadService;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.user.MyPageProfileService;
import com.DOH.DOH.service.user.MyPageService;
import com.DOH.DOH.service.user.PortFolioUploadService;
import com.DOH.DOH.service.user.UserSessionService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageProfileMapper myPageProfileMappermapper;
    private final MyPageProfileService service;
    private final MyPageService myPageService;
    private final PortFolioUploadMapper portFolioUploadMapper;
    private final PortFolioUploadService portFolioUploadService;
    private final AmazonS3 amazonS3;
    private final String bucketName= "doh-contest-storage";
    private final UserSessionService userSessionService;
    private final MyPageMapper myPageMapper;
    private final ContestUploadService contestUploadService;
    private final ContestListService contestListService;

    @GetMapping("/users/mypage")
    public String myPage(Long id, Model model, MyPageDTO myPageDTO, MyPageProfileDTO profile) {

        //유저 이메일 가져오기
        String userEmail = userSessionService.userEmail();

        myPageDTO = myPageService.findByuserEmail(userEmail);
        if(myPageDTO == null){
            myPageMapper.insertUserEmail(userEmail);
        }

        //유저 이메일로 생성한 컨테스트 목록 가져오기
        List<ContestUploadDTO>contestList = contestUploadService.getContestsByUserEmail(userEmail);
        List<ApplyDTO>getApplicationList = contestListService.getApplicationList(userEmail);

        log.info("ssss:{}",getApplicationList);

        model.addAttribute("contestList",contestList);
        model.addAttribute("applicationList",getApplicationList);
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
    public String uploadFragment(Model model, PortFolioUploadDTO uploadDTO) {
       String userEmail = userSessionService.userEmail();
       uploadDTO = portFolioUploadService.findByUserEmail(userEmail);
       model.addAttribute("uploadDTO", uploadDTO);
       return "user/MyPageUpload";
   }
   @PostMapping("/users/mypage/upload")
   public String personalUpload(@RequestParam("personalWork") MultipartFile file, Model model, PortFolioUploadDTO uploadDTO) throws IOException{
       String userEmail = userSessionService.userEmail();
        uploadDTO = portFolioUploadService.findByUserEmail(userEmail);
       if (file != null && !file.isEmpty()) {
           String newFileName = uploadFileToS3Bucket(file);
           uploadDTO.setPersonalFilePath(newFileName);
       }
       System.out.println("uploadDTO" + uploadDTO);
       System.out.println("uploadDTO" + uploadDTO);
       System.out.println("uploadDTO" + uploadDTO);
       System.out.println("uploadDTO" + uploadDTO);
       System.out.println("uploadDTO" + uploadDTO);
       portFolioUploadService.insert(uploadDTO);
       return "redirect:/mypage/portfolio";
   }


   @GetMapping("/users/mypage/profile/write")
    public String profileWriteFragment(Model model, MyPageProfileDTO profile) {
        String tempEmail = "hsm3809@naver.com";
        profile = service.findByUserEmail(tempEmail);
        model.addAttribute("profile", profile);
        return "user/MyPageProfile";
   }
    @PostMapping("/users/mypage/profile/write")
    public String Postprofilewrite(Model model, MyPageProfileDTO profile) {
        String tempEmail = "hsm3809@naver.com";
        profile = service.findByUserEmail(tempEmail);
        myPageProfileMappermapper.insert(profile);
        return "redirect:/mypage";
    }
}