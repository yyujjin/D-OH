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

        return "redirect:/contest/final";  // 최종 확인 단계로 이동
    }


    // 최종 단계: 데이터베이스에 저장하고 성공 페이지로 이동
    @GetMapping("/contest/final")
    public String finalizeContest(HttpSession session) {
        // 세션에서 최종 DTO 불러오기
        ContestUploadDTO contestUploadDTO = (ContestUploadDTO) session.getAttribute("contestData");

        // DB에 저장
        contestUploadService.saveContest(contestUploadDTO);

        // 세션 초기화 (필요에 따라 유지 가능)
        session.invalidate();

        return "redirect:/contest/success";  // 성공 페이지로 리다이렉트
    }

    // 성공 페이지
    @GetMapping("/contest/success")
    public String showSuccessPage() {
        return "contest/success";  // 성공 템플릿 반환
    }
}
