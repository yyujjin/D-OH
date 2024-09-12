package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.service.list.ContestListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Slf4j
@Controller
public class ContestListController {

    @Autowired
    private ContestListService contestListService;

    @GetMapping("/contest/list")
    public String contestList(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "orderType", required = false, defaultValue = "current")String orderType, Model model){

        PagingDTO dto = new PagingDTO(page,contestListService.getTotalCount(),10);


        ArrayList<ContestListDTO> contestList = contestListService.getContestList(dto);
        model.addAttribute("contestList", contestList);
        model.addAttribute("pageMaker", dto);

        return "list/contestList";
    }

    @GetMapping("/contest/application/terms")
    public String applictionTerms(){
        return "list/apply_1";
    }

    @GetMapping("/contest/application/write")
    public String applictionWrite(){
        return "list/apply_2";
    }
}
