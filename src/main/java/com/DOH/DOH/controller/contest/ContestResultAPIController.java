package com.DOH.DOH.controller.contest;

import com.DOH.DOH.service.contest.ContestAwardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/users/contest")
@RestController
@Slf4j
public class ContestResultAPIController {

    private final ContestAwardService contestAwardService;

    public ContestResultAPIController(ContestAwardService contestAwardService) {
        this.contestAwardService = contestAwardService;
    }

    @PostMapping("/award")
    public ResponseEntity submitAwardResults(@RequestBody Map<String, String> formData ) {

        contestAwardService.saveAwardResult(formData);

        return ResponseEntity.ok().build();
    }
}
