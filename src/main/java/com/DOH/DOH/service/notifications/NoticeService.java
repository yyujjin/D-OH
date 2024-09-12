package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import org.springframework.ui.Model;

public interface NoticeService {

    // 공지사항 목록 조회 (페이징 처리)
    void getNoticeList(int page, Model model);

    // 전체 페이지 수 계산 메서드 (페이지네이션)
    int getTotalPages();

    // 공지사항 상세 조회
    NoticeDTO getNoticeDetail(int noticeNum);

    // 공지사항 정식 저장
    void saveNotice(NoticeDTO noticeDTO);

    // 공지사항 임시 저장 메서드
    void saveTempNotice(NoticeDTO noticeDTO);

    // 공지사항 수정
    void updateNotice(NoticeDTO noticeDTO);

    // 공지사항 삭제
    void deleteNotice(int noticeNum);
}
