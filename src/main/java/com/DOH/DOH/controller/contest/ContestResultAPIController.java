package com.DOH.DOH.controller.contest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/contest")
@RestController
@Slf4j
public class ContestResultAPIController {
    @PostMapping("/award")
    public ResponseEntity submitAwardResults(@RequestBody Map<String, String> formData ) {
        //유저 이메일이나 컨테스트 id 받아서 들어온 상금 이랑 설정한 상금 인원 같아야 제출가능
        //{user1=none, user2=none}

        log.info("요청 : {}",formData);
        return ResponseEntity.ok("Dd");
    }
}
