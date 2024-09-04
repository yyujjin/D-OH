package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import org.springframework.ui.Model;

public interface NoticeService {

    // 공지사항 목록 조회 (페이징 처리)
    void getNoticeList(int page, Model model);

    // 공지사항 상세 조회
//    Notice getNoticeDetail(int noticeNum);

    // 공지사항 작성
    void writeNotice(NoticeDTO notice);

    // 공지사항 수정
    void updateNotice(NoticeDTO notice);

    // 공지사항 삭제
    void deleteNotice(int noticeNum);
}
