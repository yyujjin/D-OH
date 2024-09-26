package com.DOH.DOH.dto.list;

import lombok.Data;

@Data
public class PortFolioDTO {
    private int id;// 포토폴리오 번호
    private String userEmail;
    private String title;// 포토폴리오 제목

    private String content;// 포토폴리오 내용

    private String userNickname;// 닉네임

    private String profileIntroduce;// 디자이너 소개
    private String messageStart;// 연락 가능 시간(시작)
    private String messageEnd;// 연락 가능 시간(종료)
    private String myPageUrl;// 포토폴리오 URL

    private int commissionStatus;// 1:1 의뢰 여부
    private int profileHit;// 조회수
    private String personalWork;// 업로드 이미지 url 경로
}
