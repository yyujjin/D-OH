package com.DOH.DOH.dto.contest;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ContestUploadDTO {
    private int conNum; // 콘테스트 번호
    private String conEmail; // 콘테스트 등록 업체 이메일

    private String conCompanyName; // 회사명
    private String conOneLiner; // 한줄 소개
    private String conType; // 업종
    private String conLogoName; // 로고명
    private String conBriefing; // 브리핑

//    파일 업로드
//    private MultipartFile conFilePath; // 경로
//    private String fileName; // 파일명
//    private String image; // 이미지

    private String conTitle; // 콘테스트 제목

    private Integer conFirstPrice = 0; // 1등
    private Integer conSecondPrice = 0; // 2등
    private Integer conThirdPrice = 0; // 3등
    private Integer conFirstPeople = 0; // 1등 수
    private Integer conSecondPeople = 0; // 2등 수
    private Integer conThirdPeople = 0; // 3등 수

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conEndDate;
}