package com.DOH.DOH.dto.list;

import lombok.Data;

@Data
public class ContestListDTO {

    private int rownum;//콘테스트 번호 역순
//    private Long id;//콘테스트 번호 : 타입 불일치로 변경함
    private int id;//콘테스트 번호
    private String conType;//콘테스트 유형(업종)
    private String conTitle;//콘테스트 제목
    private int totalPrice;//콘테스트 총 상금
    private String conEndDate;//콘테스트 마감일(마감일 전체)
    private int endDate;//콘테스트 남은 기간(마감일)
    private String conCompanyName;//콘테스트 주최자명
    private int conHit;//콘테스트 조회수
}
