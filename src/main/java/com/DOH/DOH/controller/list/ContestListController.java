package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.service.list.ContestListService;
import com.DOH.DOH.service.user.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String contestList(){

        return "list/contestList";
    }

    @GetMapping("/list_ajax")
    public ModelAndView contestList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(name = "orderType")String orderType,
                                    Principal principal){

        log.info("orderType 테스트!! "+orderType);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/list/contestWrap_ajax");

        if (principal != null) { // principal이 null이 아닌 경우 처리
            String userEmail = principal.getName();
            if (userEmail != null) {
                ArrayList<Integer> scrapList = contestListService.getScrapList(userEmail);
                log.info("scrapList" + scrapList);
                mav.addObject("scrapList", scrapList);
            }
        }

        PagingDTO dto = new PagingDTO(page,contestListService.getTotalCount(),10);

        ArrayList<ContestListDTO> contestList = contestListService.getContestList(dto, orderType);

        mav.addObject("contestList", contestList);
        mav.addObject("pageMaker", dto);

        return mav;
    }

    @PostMapping("/scrap")
    @ResponseBody
    public int scrap(Principal principal, int contestId){
        log.info("스크랩 할 번호 ---->"+contestId);
        int result = 0;
        if (principal != null) { // principal이 null이 아닌 경우 처리
            String userEmail = principal.getName();
            log.info("email test!! " + userEmail);
            result = contestListService.scrap(userEmail, contestId);
        }else{
            result = 2;
        }

        return result;
    }

    @PostMapping("/hitUp")
    @ResponseBody
    public void hitUp(int contestId){
        log.info("콘테스트 번호 이동"+contestId);
        contestListService.hitUp(contestId);
    }

}
