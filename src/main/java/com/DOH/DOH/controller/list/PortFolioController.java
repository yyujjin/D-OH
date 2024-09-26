package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.dto.list.PortFolioDTO;
import com.DOH.DOH.service.list.PortFolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/portfolio")
public class PortFolioController {

    @Autowired
    PortFolioService portFolioService;

    @GetMapping("/list")
    public String portfolioList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "false")String orderType,
                                Model model) {

        PagingDTO dto = new PagingDTO(page, portFolioService.getTotalCount(),12);

        ArrayList<PortFolioDTO> portFolioList = portFolioService.getPortFolioList(dto);

        model.addAttribute("portfolioList", portFolioList);
        model.addAttribute("pageMaker",dto);
        return "list/portfolioList";

    }

    @GetMapping("/list/detail")
    public String viewPortfolio(int portfolioId, Model model)
    {
        PortFolioDTO portFolioDTO = portFolioService.getPortfolioInfo(portfolioId);
        model.addAttribute("portFolio",portFolioDTO);
        return "user/portfolioDetail";
    }



    @PostMapping("/hitUp")
    @ResponseBody
    public void hitUp(int id){
        log.info("portFolio id check!! : "+id);
        portFolioService.hitUp(id);
    }

}
