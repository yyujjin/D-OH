package com.DOH.DOH.dto.contest;

import lombok.Data;

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

    private int conFirstPrice; // 1등
    private int conSecondPrice; // 2등
    private int conThirdPrice; // 3등
    private int conFirstPeople; // 1등 수
    private int conSecondPeople; // 2등 수
    private int conThirdPeople; // 3등 수

    private Date conStartDate; // 콘테스트 시작일
    private Date conEndDate; // 콘테스트 종료일
}
