package com.DOH.DOH.dto.list;

import lombok.Data;

@Data
public class ContestListDTO {

    private int id;//콘테스트 번호
    private String conType;//콘테스트 유형(업종)
    private String conTitle;//콘테스트 제목
    private int totalPrice;//콘테스트 총 상금
    private String conEndDate;//콘테스트 마감일
    private int endDate;//콘테스트 남은 기간(마감일)
    private String conCompanyName;//콘테스트 주최자명
}
