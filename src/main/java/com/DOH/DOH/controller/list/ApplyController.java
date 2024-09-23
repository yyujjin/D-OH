package com.DOH.DOH.controller.list;
import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.list.S3Service;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/contest")
public class ApplyController {

    @Autowired
    private ContestListService contestListService;
    private S3Service s3Service;  // S3 업로드 서비스를 추가로 주입

    @GetMapping("/application/terms")
    public String applictionTerms(HttpSession session){
        String email= (String) session.getAttribute("userEmail");
        log.info("email 체크!! -> "+email);
        return "list/applicationTerms";
    }

    @GetMapping("/application/write")
    public String applictionWrite(String userEmail, Model model){
        model.addAttribute("userEmail", userEmail);
        return "list/applicationWrite";
    }

    @PostMapping("/application/upload")
    public String handleUpload(@RequestParam("image") MultipartFile[] files,
                               @RequestParam HashMap<String, String> param,
                               Model model) {
        try {
            // 1. 파일 업로드 처리 (S3)
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileUrl = s3Service.uploadFile(file);
                    log.info("File uploaded to S3: " + fileUrl);
                }
            }

            // 2. 제목 등 입력값 처리 (DB)
            ApplyDTO contest = new ApplyDTO();
            contest.setApplyTitle(param.get("applyTitle"));
            contest.setApplyContent(param.get("applyContent"));
            log.info("applyTitle!!"+contest.getApplyTitle());
            log.info("applyContent!!"+contest.getApplyContent());
            contestListService.saveContestApply(contest);

            return "redirect:/contest/list";  // 업로드 후 컨테스트 목록 페이지로 이동
        } catch (Exception e) {
            log.error("업로드 중 오류 발생", e);
            model.addAttribute("error", "업로드 중 문제가 발생했습니다.");
            return "list/applicationWrite";  // 오류 시 다시 작성 페이지로 이동
        }
    }
}
