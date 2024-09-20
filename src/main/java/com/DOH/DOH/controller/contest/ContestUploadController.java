package com.DOH.DOH.controller.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.service.contest.ContestUploadService;
import com.DOH.DOH.service.user.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ContestUploadController {

    private final UserSessionService userSessionService;

    public ContestUploadController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Autowired
    private ContestUploadService contestUploadService;

    // 1단계: 첫 번째 폼 (회사 정보 등) 보여주기
    @GetMapping("/contest")
    public String showContestForm(Model model) {
        // DB에서 업종 목록 가져오기
        List<String> contestTypes = contestUploadService.getContestTypes();
        model.addAttribute("contestTypes", contestTypes);
        model.addAttribute("contestUploadDTO", new ContestUploadDTO());
        return "contest/ContestUpload";
    }

    // 1단계: 폼 데이터를 세션에 저장하고 다음 단계로 이동
    @PostMapping("/contest")
    public String submitContest(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        log.info("Received ContestUploadDTO: {}", contestUploadDTO);
        session.setAttribute("contestData", contestUploadDTO);
        // 세션에 저장된 값 확인
        log.info("Stored in session: {}", session.getAttribute("contestData"));
        return "redirect:/contest/budget";  // 2단계로 이동
    }

    // 2단계: 상금 및 기간 설정 폼 보여주기
    @GetMapping("/contest/budget")
    public String showContestForm1(Model model, HttpSession session) {
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");
        model.addAttribute("contestUploadDTO", contestUploadDTO);
        return "contest/ContestUpload1";  // 2단계 폼 템플릿
    }

    // 2단계: 폼 데이터를 세션에 업데이트하고 최종 저장 단계로 이동
    @PostMapping("/contest/budget")
    public String submitContest1(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        ContestUploadDTO existingContestData = (ContestUploadDTO) session.getAttribute("contestData");
        log.info("ContestUploadDTO before copy: {}", existingContestData);
        BeanUtilsHelper.copyNonNullProperties(contestUploadDTO, existingContestData);
        session.setAttribute("contestData", existingContestData);
        log.info("Updated ContestUploadDTO in session: {}", existingContestData);
        return "redirect:/contest/payment";  // 결제 정보 확인 페이지로 이동
    }

    // 결제 정보 확인 페이지
    @GetMapping("/contest/payment")
    public String showPaymentPage(HttpSession session, Model model) {
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");
        log.info("ContestUploadDTO in payment page: {}", contestUploadDTO); // 세션에서 가져온 DTO 확인
        if (contestUploadDTO == null) {
            return "redirect:/contest";  // 세션에 데이터가 없으면 처음으로 리다이렉트
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

    @PostMapping("/contest/payment/complete")
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

            // 리다이렉트 URL 반환
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/contest/view?contestNum=" + contestData.getConNum());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 처리 중 오류 발생: ", e);
            return ResponseEntity.badRequest().body(Map.of("message", "결제 처리 중 오류가 발생했습니다."));
        }
    }

    // 결제 완료 후 contestView로 리다이렉트하는 새로운 메서드
    @GetMapping("/contest/payment/success")
    public String redirectToContestView(HttpSession session) {
        Long contestNum = (Long) session.getAttribute("contestNum");
        if (contestNum != null) {
            return "redirect:/contest/view?contestNum=" + contestNum;
        } else {
            return "redirect:/contest";  // contestNum가 없으면 초기화면으로
        }
    }

    // 주문번호 생성기 (필요시)
    @PostMapping("/contest/createOrder")
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

        return "contest/ContestView";  // 뷰로 이동
    }

}
