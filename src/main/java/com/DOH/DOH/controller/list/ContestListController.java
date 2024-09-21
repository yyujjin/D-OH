package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.user.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/contest")
public class ContestListController {

    @Autowired
    private ContestListService contestListService;

    @GetMapping("/list")
    public String contestList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(name = "orderType", defaultValue = "current")String orderType,
                              Principal principal,Model model){

        String userEmail = principal.getName();
        if(userEmail != null){
            model.addAttribute("userEmail", userEmail);
        }
        PagingDTO dto = new PagingDTO(page,contestListService.getTotalCount(),10);

        ArrayList<ContestListDTO> contestList = contestListService.getContestList(dto);
        model.addAttribute("contestList", contestList);
        model.addAttribute("pageMaker", dto);

        return "list/contestList";
    }

    @PostMapping("/scrap")
    @ResponseBody
//    public void scrap(Principal principal, int contestId){
    public void scrap(@RequestParam HashMap<String,String> param){
//        log.info("스크랩 할 번호"+contestId);
//        String email = principal.getName();
//        log.info("email test!! "+email);


//            boolean scrap = contestListService.scrap(email, contestId);
            boolean scrap = contestListService.scrap(param);
//        contestListService.hitUp(contestId);
    }

    @PostMapping("/hitUp")
    @ResponseBody
    public void hitUp(int contestId){
        log.info("콘테스트 번호 이동"+contestId);
        contestListService.hitUp(contestId);
    }

}
