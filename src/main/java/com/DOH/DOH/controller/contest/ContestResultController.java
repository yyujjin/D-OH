package com.DOH.DOH.controller.contest;

import com.DOH.DOH.service.contest.ContestUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ContestResultController {

    private final ContestUploadService contestUploadService;

    public ContestResultController(ContestUploadService contestUploadService) {
        this.contestUploadService = contestUploadService;
    }


    @GetMapping("/result")
    public String test() {
        return "/contest/result";
    }

    @GetMapping("/contest/award")
    public String contestAward(@RequestParam Long contestNum, Model model) {

        model.addAttribute("applicantList",contestUploadService.getContestApplicants(contestNum));
        return "contest/contestAward";
    }


}
