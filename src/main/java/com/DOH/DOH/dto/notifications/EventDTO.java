package com.DOH.DOH.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long eventNum;
    private String eventTitle;
    private String eventContent;
    private LocalDate eventCreateTime;
    private String eventImageName;
    private MultipartFile eventImageUrl;
    private int userNum;
    private boolean eventTempSave;
    private String formattedCreateTime; // 생성일
    private String formattedStartDate; // 이벤트 시작일
    private String formattedEndDate; // 이벤트 마감일
    //이벤트 시작일, 마감일 설정
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
}
