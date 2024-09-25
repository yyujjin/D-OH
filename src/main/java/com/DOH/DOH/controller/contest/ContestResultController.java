package com.DOH.DOH.controller.contest;

import com.DOH.DOH.service.contest.ContestAwardService;
import com.DOH.DOH.service.contest.ContestUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/users/contest")
public class ContestResultController {

    private final ContestUploadService contestUploadService;
    private final ContestAwardService contestAwardService;

    public ContestResultController(ContestUploadService contestUploadService, ContestAwardService contestAwardService) {
        this.contestUploadService = contestUploadService;
        this.contestAwardService = contestAwardService;
    }


    //결과 페이지 렌더링 (트로피)
    @GetMapping("/result")
    public String result (@RequestParam Long contestNum,Model model) {
        model.addAttribute("award",contestAwardService.viewResult(contestNum));
        log.info("ddddd:{}",contestAwardService.viewResult(contestNum));
        return "/contest/result";
    }

    //시상식 페이지 렌더링
    @GetMapping("/award")
    public String contestAward(@RequestParam Long contestNum, Model model) {

        model.addAttribute("applicantList",contestUploadService.getContestApplicants(contestNum));
        model.addAttribute("contestNum",contestNum);

        return "contest/contestAward";
    }

}
