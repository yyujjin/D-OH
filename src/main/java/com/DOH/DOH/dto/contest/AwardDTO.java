package com.DOH.DOH.dto.contest;

import lombok.Data;

import java.awt.desktop.ScreenSleepEvent;

@Data
public class AwardDTO {
    private Long id;
    private String userEmail; //컨테스트 개최자
    private String firstPlace; //1등
    private String secondPlace; //2등
    private String thirdPlace; //3등

}
