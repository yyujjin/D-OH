package com.DOH.DOH.controller.contest;

import com.DOH.DOH.service.contest.ContestAwardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/contest")
@RestController
@Slf4j
public class ContestResultAPIController {

    private final ContestAwardService contestAwardService;

    public ContestResultAPIController(ContestAwardService contestAwardService) {
        this.contestAwardService = contestAwardService;
    }

    @PostMapping("/award")
    public ResponseEntity submitAwardResults(@RequestBody Map<String, String> formData ) {
        //유저 이메일이나 컨테스트 id 받아서 들어온 상금 이랑 설정한 상금 인원 같아야 제출가능
        //{user1=none, user2=none}

        contestAwardService.saveAwardResult(formData);
        log.info("요청 : {}",formData);

        return ResponseEntity.ok().build();
    }
}
