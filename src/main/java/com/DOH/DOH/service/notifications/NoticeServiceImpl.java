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

    private static final int PAGE_SIZE = 10; // 페이지당 공지사항 수

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 공지사항 목록을 가져오는 메서드
     * @param page 현재 페이지 번호 (페이징 처리용)
     * @return 공지사항 목록
     */
    @Override
    public List<NoticeDTO> getNoticeList(int page) {
        if (page < 1) {
            page = 1; // 최소 페이지 번호는 1
        }

        int offset = (page - 1) * PAGE_SIZE; // 페이징 처리를 위한 offset 계산

        // 공지사항 목록 가져오기
        List<NoticeDTO> noticeList = noticeMapper.getNoticeList(offset, PAGE_SIZE);

        // 리스트가 null이면 빈 리스트로 초기화
        if (noticeList == null) {
            noticeList = new ArrayList<>();
        }

        return noticeList;
    }

    /**
     * 전체 공지사항 수를 기반으로 총 페이지 수를 계산하는 메서드
     * @return 총 페이지 수
     */
    @Override
    public int getTotalPages() {
        int totalNotices = noticeMapper.getTotalNotices(); // 공지사항 총 개수 가져오기
        return (int) Math.ceil((double) totalNotices / PAGE_SIZE); // 총 페이지 수 계산
    }

    /**
     * 공지사항 번호로 공지사항을 조회하는 메서드
     * @param noticeNum 공지사항 번호
     * @return 공지사항 DTO 객체
     */
    @Override
    public NoticeDTO getNoticeById(Long noticeNum) {
        return noticeMapper.selectNoticeById(noticeNum);
    }

    /**
     * 새로운 공지사항을 작성하는 메서드
     * @param noticeDTO 공지사항 DTO 객체 (제목, 내용, 작성자 등)
     */
    @Override
    public void noticeRegister(NoticeDTO noticeDTO, Model model){
        // 공지사항 제목이 빈 값이 아닐 경우 설정 (필요 시에만)
        if (noticeDTO.getNoticeTitle() == null || noticeDTO.getNoticeTitle().isEmpty()) {
            noticeDTO.setNoticeTitle("제목 없음"); // 기본 제목 설정
        }

        noticeMapper.insertNotice(noticeDTO); // 공지사항 저장
    }

    /**
     * 공지사항 임시 저장 메서드
     * @param noticeDTO 임시 저장할 공지사항 객체
     */
    @Override
    public void saveTempNotice(NoticeDTO noticeDTO) {
        noticeDTO.setNoticeTempSave(true); // 임시 저장으로 설정
        noticeMapper.insertNotice(noticeDTO);
    }

    /**
     * 기존 공지사항을 수정하는 메서드
     * @param noticeDTO 수정된 공지사항 DTO 객체
     */
    @Override
    public void noticeUpdate(NoticeDTO noticeDTO, Model model){
        noticeMapper.updateNotice(noticeDTO);
    }

    /**
     * 공지사항을 삭제하는 메서드
     * @param noticeNum 삭제할 공지사항 번호
     */
    @Override
    public void deleteNotice(Long noticeNum) {
        noticeMapper.deleteNotice(noticeNum);
    }
}
