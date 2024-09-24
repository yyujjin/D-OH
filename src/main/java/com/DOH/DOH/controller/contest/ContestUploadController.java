package com.DOH.DOH.controller.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.service.common.S3FileUploadService;
import com.DOH.DOH.service.contest.ContestUploadService;
import com.DOH.DOH.service.user.UserSessionService;
import com.amazonaws.services.s3.AmazonS3;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ContestUploadController {

    private final UserSessionService userSessionService;
    private final  S3FileUploadService  s3FileUploadService;
    private final AmazonS3 amazonS3;
    private final String bucketName= "doh-contest-storage";

    @Autowired
    public ContestUploadController(UserSessionService userSessionService, S3FileUploadService s3FileUploadService, AmazonS3 amazonS3) {
        this.userSessionService = userSessionService;
        this.s3FileUploadService =  s3FileUploadService;
        this.amazonS3 = amazonS3;
    }
    @Autowired
    private ContestUploadService contestUploadService;

    // 1단계: 첫 번째 폼 (회사 정보 등) 보여주기
//    @GetMapping("/users/contest")
    @GetMapping("/users/contest")
    public String showContestForm(Model model) {
        // DB에서 업종 목록 가져오기
        List<String> contestTypes = contestUploadService.getContestTypes();
        model.addAttribute("contestTypes", contestTypes);
        model.addAttribute("contestUploadDTO", new ContestUploadDTO());
        return "contest/ContestUpload";
    }

    // Summernote에서 이미지 업로드 시 호출되는 핸들러
    @PostMapping("/users/contest/uploadImage")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // S3에 파일을 업로드하고 URL을 받음
            String imageUrl = s3FileUploadService.uploadFileToS3Bucket(file);

            // URL을 클라이언트로 반환
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);  // Summernote는 'url' 필드를 필요로 함
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // 오류 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "이미지 업로드 중 오류가 발생했습니다."));
        }
    }

    // 1단계: 폼 데이터를 세션에 저장하고 다음 단계로 이동
    @PostMapping("/users/contest")
    public String submitContest(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        log.info("Received ContestUploadDTO: {}", contestUploadDTO);
        session.setAttribute("contestData", contestUploadDTO);
        // 세션에 저장된 값 확인
        log.info("Stored in session: {}", session.getAttribute("contestData"));
        return "redirect:/users/contest/budget";  // 2단계로 이동
    }

    // 2단계: 상금 및 기간 설정 폼 보여주기
    @GetMapping("/users/contest/budget")
    public String showContestForm1(Model model, HttpSession session) {
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");
        model.addAttribute("contestUploadDTO", contestUploadDTO);
        return "contest/ContestUpload1";  // 2단계 폼 템플릿
    }

    // 2단계: 폼 데이터를 세션에 업데이트하고 최종 저장 단계로 이동
    @PostMapping("/users/contest/budget")
    public String submitContest1(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        ContestUploadDTO existingContestData = (ContestUploadDTO) session.getAttribute("contestData");
        log.info("ContestUploadDTO before copy: {}", existingContestData);
        BeanUtilsHelper.copyNonNullProperties(contestUploadDTO, existingContestData);
        session.setAttribute("contestData", existingContestData);
        log.info("Updated ContestUploadDTO in session: {}", existingContestData);
        return "redirect:/users/contest/payment";  // 결제 정보 확인 페이지로 이동
    }

    // 결제 정보 확인 페이지
    @GetMapping("/users/contest/payment")
    public String showPaymentPage(HttpSession session, Model model) {
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");
        log.info("ContestUploadDTO in payment page: {}", contestUploadDTO); // 세션에서 가져온 DTO 확인
        if (contestUploadDTO == null) {
            return "redirect:/users/contest";  // 세션에 데이터가 없으면 처음으로 리다이렉트
        }
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("contestUploadDTO", contestUploadDTO);
        // 상금 총계 계산
        int totalPrize = (contestUploadDTO.getConFirstPrice() * contestUploadDTO.getConFirstPeople()) +
                (contestUploadDTO.getConSecondPrice() * contestUploadDTO.getConSecondPeople()) +
                (contestUploadDTO.getConThirdPrice() * contestUploadDTO.getConThirdPeople());
        model.addAttribute("totalPrize", totalPrize);
        return "contest/ContestPay2";  // 결제 페이지로 이동
    }

    @PostMapping("/users/contest/payment/complete")
    public ResponseEntity<Map<String, String>> completePayment(@RequestBody Map<String, String> paymentData, HttpSession session) {
        String impUid = paymentData.get("imp_uid");
        String merchantUid = paymentData.get("merchant_uid");
        ContestUploadDTO contestData = (ContestUploadDTO) session.getAttribute("contestData");

        if (contestData == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "세션에 콘테스트 데이터가 없습니다."));
        }

        try {
            // 주문번호를 설정
            contestData.setOrderNumber(merchantUid); // merchantUid를 주문번호로 설정

            // DB에 모든 정보 저장 (콘테스트 정보)
            contestUploadService.saveContest(contestData); // 콘테스트 정보 저장

            // 세션에서 contestNum 저장
            session.setAttribute("contestNum", contestData.getConNum());

            // 세션 초기화
            session.removeAttribute("contestData");

            // 리다이렉트 URL 반환3
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/contest/view?contestNum=" + contestData.getConNum());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 처리 중 오류 발생: ", e);
            return ResponseEntity.badRequest().body(Map.of("message", "결제 처리 중 오류가 발생했습니다."));
        }
    }

    // 결제 완료 후 contestView로 리다이렉트하는 새로운 메서드
    @GetMapping("/users/contest/payment/success")
    public String redirectToContestView(HttpSession session) {
        Long contestNum = (Long) session.getAttribute("contestNum");
        if (contestNum != null) {
            return "redirect:/contest/view?contestNum=" + contestNum;
        } else {
            return "redirect:/users/contest";  // contestNum가 없으면 초기화면으로
        }
    }

    // 주문번호 생성기 (필요시)
    @PostMapping("/users/contest/createOrder")
    public ResponseEntity<Map<String, String>> createOrder() {
        String orderNumber = contestUploadService.generateOrderNumber();
        Map<String, String> response = new HashMap<>();
        response.put("orderNumber", orderNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contest/view")
    public String contestView(@RequestParam Long contestNum, Model model) {
        // 서비스 계층을 통해 DB에서 콘테스트 데이터를 가져옴
        ContestUploadDTO contestUploadDTO = contestUploadService.findContestById(contestNum);

        // 모델에 DTO 객체 자체를 추가
        model.addAttribute("contestUploadDTO", contestUploadDTO);

        //컨테스트 지원자 목록 가져오기
        List<String>applicantList = contestUploadService.getContestApplicants(contestNum);
        //model.addAttribute("applicantList",applicantList);
        log.info("가져온 지원자 목록:{}",applicantList);

        return "contest/ContestView";  // 뷰로 이동
    }


}
