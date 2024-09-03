package com.DOH.DOH.controller.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.service.contest.ContestUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ContestUploadController {
    @Autowired
    private ContestUploadService contestUploadService;

    @GetMapping("/contest")
    public String showContestForm(Model model) {
        model.addAttribute("contestUploadDTO", new ContestUploadDTO());
        return "contest/ContestUpload";
    }

    @PostMapping("/contest")
    public String submitContest(@ModelAttribute ContestUploadDTO contestUploadDTO) {

        contestUploadService.saveContest(contestUploadDTO);
        return "redirect:/contest/success";
    }

    @GetMapping("/contest/success")
    public String showSuccessPage() {
        return "contest/ContestUpload";
    }
}
