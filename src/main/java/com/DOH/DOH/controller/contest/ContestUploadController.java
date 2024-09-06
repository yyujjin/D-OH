package com.DOH.DOH.controller.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.service.contest.ContestUploadService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ContestUploadController {
    @Autowired
    private ContestUploadService contestUploadService;

    // 1단계: 첫 번째 폼 (회사 정보 등) 보여주기
    @GetMapping("/contest")
    public String showContestForm(Model model) {
        model.addAttribute("contestUploadDTO", new ContestUploadDTO());
        return "contest/ContestUpload";
    }

    // 1단계: 폼 데이터를 세션에 저장하고 다음 단계로 이동
    @PostMapping("/contest")
    public String submitContest(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        // DTO 전체를 세션에 저장
        session.setAttribute("contestData", contestUploadDTO);
        return "redirect:/contest1";  // 2단계로 이동
    }

    // 2단계: 상금 및 기간 설정 폼 보여주기
    @GetMapping("/contest1")
    public String showContestForm1(Model model, HttpSession session) {
        // 세션에 저장된 데이터를 모델에 추가하여 폼에 보여줌
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");
        model.addAttribute("contestUploadDTO", contestUploadDTO);

        // 등수 관련 데이터가 있는지 확인하여 모델에 추가
        return "contest/ContestUpload1";  // 2단계 폼 템플릿
    }

    // 2단계: 폼 데이터를 세션에 업데이트하고 최종 저장 단계로 이동
    @PostMapping("/contest1")
    public String submitContest1(@ModelAttribute ContestUploadDTO contestUploadDTO, HttpSession session) {
        // 세션에 저장된 기존 DTO 불러오기
        ContestUploadDTO existingContestData = (ContestUploadDTO) session.getAttribute("contestData");

        // 2단계에서 입력된 데이터를 기존 DTO에 업데이트
        existingContestData.setConTitle(contestUploadDTO.getConTitle());
        existingContestData.setConFirstPrice(contestUploadDTO.getConFirstPrice());
        existingContestData.setConSecondPrice(contestUploadDTO.getConSecondPrice());
        existingContestData.setConThirdPrice(contestUploadDTO.getConThirdPrice());
        existingContestData.setConFirstPeople(contestUploadDTO.getConFirstPeople());
        existingContestData.setConSecondPeople(contestUploadDTO.getConSecondPeople());
        existingContestData.setConThirdPeople(contestUploadDTO.getConThirdPeople());
        existingContestData.setConStartDate(contestUploadDTO.getConStartDate());
        existingContestData.setConEndDate(contestUploadDTO.getConEndDate());

        // 업데이트된 DTO를 다시 세션에 저장
        session.setAttribute("contestData", existingContestData);

        return "redirect:/contest/payment";  // 결제 정보 확인 페이지로 이동
    }

    // 결제 정보 확인 페이지
    @GetMapping("/contest/payment")
    public String showPaymentPage(HttpSession session, Model model) {
        // 세션에서 콘테스트 데이터를 가져옴
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");

        if (contestUploadDTO == null) {
            return "redirect:/contest";  // 세션에 데이터가 없으면 처음으로 리다이렉트
        }

        // 각 등수의 상금과 명수를 곱해서 총 상금 계산
        int firstTotal = contestUploadDTO.getConFirstPrice() * contestUploadDTO.getConFirstPeople();
        int secondTotal = contestUploadDTO.getConSecondPrice() * contestUploadDTO.getConSecondPeople();
        int thirdTotal = contestUploadDTO.getConThirdPrice() * contestUploadDTO.getConThirdPeople();

        int totalPrize = firstTotal + secondTotal + thirdTotal;

        // 필요한 데이터를 모델에 추가하여 뷰에 전달
        model.addAttribute("contestUploadDTO", contestUploadDTO);
        model.addAttribute("totalPrize", totalPrize);
        model.addAttribute("conCompany", contestUploadDTO.getConCompanyName());  // 회사명 추가
//        model.addAttribute("이메일이름", 문균호DTO.get이메일 이름());  // 회사명 추가

        // DB에 저장
        contestUploadService.saveContest(contestUploadDTO);

        return "contest/ContestPay2";  // 파일명을 올바르게 변경
    }

    // 결제 정보 확인 후 DB 저장
    @PostMapping("/contest/payment/submit")
    public String submitPayment(HttpSession session) {
        // 세션에서 최종 DTO 불러오기
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");

        if (contestUploadDTO == null) {
            return "redirect:/contest";  // 세션에 데이터가 없으면 처음으로 리다이렉트
        }

        // 세션 초기화 (필요시 유지할 수 있음)
        session.invalidate();

        return "contest/PaymentSuccess";  // 결제 성공 페이지로 이동
    }

}
