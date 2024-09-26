package com.DOH.DOH.controller.list;
import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.service.common.S3FileUploadService;
import com.DOH.DOH.service.contest.ContestUploadService;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.list.S3Service;
import com.DOH.DOH.service.user.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/api/users/contest")
public class ApplyController {

    private final ContestListService contestListService;
    private final UserSessionService userSessionService;
    private final S3FileUploadService s3FileUploadService;

    public ApplyController(ContestListService contestListService, UserSessionService userSessionService, S3FileUploadService s3FileUploadService) {
        this.contestListService = contestListService;
        this.userSessionService = userSessionService;
        this.s3FileUploadService = s3FileUploadService;
    }


    //컨테스트 참여 약관동의 페이지 로드
    @GetMapping("/application/terms")
    public String applictionTerms(@RequestParam(name = "contestNum")Long id, Model model)
    {
        log.info("id test --> "+id);

        ContestListDTO dto = contestListService.contestInfo(id);
        int count = contestListService.getApplyCount(id);

        model.addAttribute("contestDTO", dto);
        model.addAttribute("count", count);
        return "list/applicationTerms";
    }

    //컨테스트 작성 페이지 로드
    @GetMapping("/application/write")
    public String applictionWrite(@RequestParam(name = "contestNum")Long id, Principal principal, Model model){
        String userEmail = " ";

        if (principal != null) { // principal이 null이 아닌 경우 처리
            userEmail = principal.getName();
            log.info("email test - -->"+userEmail);
            model.addAttribute("userEmail", userEmail);
        }else{
            return "user/login";
        }

        ContestListDTO dto = contestListService.contestInfo(id);
        int count = contestListService.getApplyCount(id);

        model.addAttribute("contestDTO", dto);
        model.addAttribute("count", count);

        return "list/applicationWrite";
    }

    //컨테스트 저장
    @PostMapping("/application/upload")
    public ResponseEntity handleUpload(@ModelAttribute ApplyDTO applyDTO,MultipartFile file) throws IOException {

        applyDTO.setUserEmail(userSessionService.userEmail());
        applyDTO.setImageUrl(s3FileUploadService.uploadFileToS3Bucket(file));
        contestListService.saveContest(applyDTO);
        return ResponseEntity.ok().build();
    }

    // conNum과 userEmail로 작성된 글 상세보기 페이지 로드
    @GetMapping("/application/detail/{conNum}/{userEmail}")
    public String getContestDetail(@PathVariable("conNum") Long conNum, @PathVariable("userEmail") String userEmail, Model model) {
        log.info("컨테스트 상세보기 conNum: " + conNum + ", userEmail: " + userEmail);

        // conNum과 userEmail로 ApplyDTO 정보 가져오기
        ApplyDTO applyDTO = contestListService.getApplyByConNumAndUserEmail(conNum, userEmail);

        if (applyDTO == null) {
            log.warn("해당 컨테스트를 찾을 수 없습니다. conNum: " + conNum + ", userEmail: " + userEmail);
            return "error/404";  // 404 페이지로 리다이렉트할 수 있음
        }

        // 모델에 컨테스트 상세정보 추가
        model.addAttribute("applyDTO", applyDTO);

        // HTML 파일 경로를 올바르게 설정
        return "list/applyInfDetail";
    }
}
