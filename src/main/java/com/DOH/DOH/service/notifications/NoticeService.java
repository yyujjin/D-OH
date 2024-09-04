package com.DOH.DOH.service.notifications;

import org.springframework.ui.Model;
import com.DOH.DOH.domain.Notice;
import java.util.List;

public interface NoticeService {

    // 공지사항 목록 조회 (페이징 처리)
    void getNoticeList(int page, Model model);

    // 공지사항 상세 조회
//    Notice getNoticeDetail(int noticeNum);

    // 공지사항 작성
    void writeNotice(Notice notice);

    // 공지사항 수정
    void updateNotice(Notice notice);

    // 공지사항 삭제
    void deleteNotice(int noticeNum);
}
