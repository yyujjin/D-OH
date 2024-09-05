package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.mapper.notifications.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 공지사항 목록을 가져와서 모델에 추가하는 메서드
     * @param page 현재 페이지 번호 (페이징 처리용)
     * @param model 공지사항 목록을 저장할 모델 객체
     */
    @Override
    public void getNoticeList(int page, Model model) {
        int offset = (page - 1) * 10; // 페이징 처리를 위한 offset 계산
        List<NoticeDTO> noticeList = noticeMapper.getNoticeList(offset); // 공지사항 목록 가져오기

        if (noticeList == null) {
            noticeList = new ArrayList<>(); // 리스트가 null이면 빈 리스트로 초기화
        }

        model.addAttribute("noticeList", noticeList); // 모델에 공지사항 목록 추가
        model.addAttribute("noticeNum", noticeList.size()); // 모델에 noticeNum 추가
    }

    /**
     * 새로운 공지사항을 작성하는 메서드
     * @param notice 공지사항 DTO 객체 (제목, 내용, 작성자 등)
     */
    @Override
    public void writeNotice(NoticeDTO notice) {
        noticeMapper.writeNotice(notice); // 공지사항 작성 로직 실행
    }

    /**
     * 기존 공지사항을 수정하는 메서드
     * @param notice 수정된 공지사항 DTO 객체
     */
    @Override
    public void updateNotice(NoticeDTO notice) {
        noticeMapper.updateNotice(notice); // 공지사항 수정 로직 실행
    }

    /**
     * 공지사항을 삭제하는 메서드
     * @param noticeNum 삭제할 공지사항 번호
     */
    @Override
    public void deleteNotice(int noticeNum) {
        noticeMapper.deleteNotice(noticeNum); // 공지사항 삭제 로직 실행
    }
}
