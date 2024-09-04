package com.DOH.DOH.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private int noticeNum;        // 공지사항 번호 (Primary Key)
    private String noticeTitle;   // 공지사항 제목
    private String noticeContent; // 공지사항 내용
    private Date noticeCreateTime; // 공지사항 작성 시간
    private int userNum;          // 작성자 번호 (Foreign Key)
    private boolean noticeTempSave; // 임시 저장 여부
}
