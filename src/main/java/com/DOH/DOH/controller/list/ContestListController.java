package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.list.S3Service;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/contest")
public class ContestListController {

    @Autowired
    private ContestListService contestListService;

    @GetMapping("/list")
    public String contestList(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "orderType", required = false, defaultValue = "current")String orderType, Model model){

        PagingDTO dto = new PagingDTO(page,contestListService.getTotalCount(),10);


        ArrayList<ContestListDTO> contestList = contestListService.getContestList(dto);
        model.addAttribute("contestList", contestList);
        model.addAttribute("pageMaker", dto);

        return "list/contestList";
    }

    @PostMapping("/hitUp")
    @ResponseBody
    public void hitUp(int contestId){
        log.info("콘테스트 번호 이동"+contestId);
        contestListService.hitUp(contestId);
    }

}
