package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.dto.notifications.NoticeDTO;

import java.util.List;

public interface NoticeService {

    // 공지사항 목록 조회 (페이징 처리)
//    List<NoticeDTO> getNoticeList(int page);
    List<NoticeDTO> getNoticeList(PagingDTO pagingDTO);

    List<NoticeDTO> getTempNoticeList();

    // 전체 페이지 수 계산 메서드 (페이지네이션)
    int getTotalPages();

    NoticeDTO getNoticeById(Long noticeNum);  // 공지사항 번호로 공지사항을 조회하는 메서드

    // 공지사항 정식 저장
    void noticeRegister(NoticeDTO noticeDTO);

    // 공지사항 임시 저장 메서드
    void saveTempNotice(NoticeDTO noticeDTO);

    // 공지사항 수정
    void noticeUpdate(NoticeDTO noticeDTO);

    // 공지사항 삭제
    void deleteNotice(Long noticeNum);

    //임시저장 수정
    void updateTempNotice(NoticeDTO noticeDTO);
}
