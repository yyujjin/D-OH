package com.DOH.DOH.controller.contest;

import com.DOH.DOH.service.contest.ContestAwardService;
import com.DOH.DOH.service.contest.ContestUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/users/contest")
@RestController
@Slf4j
public class ContestResultAPIController {

    private final ContestAwardService contestAwardService;
    private final ContestUploadService contestUploadService;

    public ContestResultAPIController(ContestAwardService contestAwardService, ContestUploadService contestUploadService) {
        this.contestAwardService = contestAwardService;
        this.contestUploadService = contestUploadService;
    }

    @PostMapping("/award")
    public ResponseEntity submitAwardResults(@RequestBody Map<String, String> formData ) {

        contestAwardService.saveAwardResult(formData);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/award/result")
    public ResponseEntity viewAwardResult(@RequestParam Long conNum){

        if (contestUploadService.isFinishedContest(conNum)==0) {
            return ResponseEntity.ok("콘테스트가 진행 중입니다. 모집 기간 종료 후 결과를 확인할 수 있습니다!");
        };

        log.info("dddd:{}",contestAwardService.viewResult(conNum));
        return ResponseEntity.ok(contestAwardService.viewResult(conNum));
    }
}
