package com.DOH.DOH.dto.list;

import lombok.Data;

@Data// 페이지 계산을 위해 Getter, Setter 필요 : @Data 추가
public class PagingDTO {
    private int startPage;     // 시작 페이지
    private int endPage;       // 끝 페이지
    private boolean prev, next; // 이전, 다음 버튼 활성화 여부
    private int total;         // 총 게시물 수
    private int currentPage;   // 현재 페이지
    private int pageSize;      // 한 페이지당 글 수

//    public PagingDTO(int currentPage, int total, int pageSize) {
    public PagingDTO(int currentPage, int total, int pageSize) {
        this.currentPage = currentPage;
        this.total = total;
        this.pageSize = pageSize;

        // 끝 페이지 계산
        this.endPage = (int) (Math.ceil(currentPage / 10.0)) * 10;

        // 시작 페이지 계산
        this.startPage = this.endPage - 9;

        // 실제 끝 페이지 (총 게시물 수에 따라 계산)
        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));
        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        // 이전 버튼 여부
        this.prev = this.startPage > 1;

        // 다음 버튼 여부
        this.next = this.endPage < realEnd;
    }
}
