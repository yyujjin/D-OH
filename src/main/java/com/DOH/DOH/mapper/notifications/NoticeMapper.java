package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    /**
     * 공지사항 목록을 가져오는 메서드 (페이징 처리)
     * @param offset 데이터베이스에서 가져올 시작 위치
     * @return 공지사항 목록
     */
    List<NoticeDTO> getNoticeList(@Param("offset") int offset);

    /**
     * 공지사항 상세 정보를 가져오는 메서드
     * @param noticeNum 공지사항 번호
     * @return 공지사항 DTO 객체
     */
    NoticeDTO getNoticeDetail(@Param("noticeNum") int noticeNum);

    /**
     * 공지사항 작성 메서드
     * @param notice 공지사항 DTO 객체
     */
    void writeNotice(NoticeDTO notice);

    /**
     * 공지사항 수정 메서드
     * @param notice 수정된 공지사항 DTO 객체
     */
    void updateNotice(NoticeDTO notice);

    /**
     * 공지사항 삭제 메서드
     * @param noticeNum 삭제할 공지사항 번호
     */
    void deleteNotice(@Param("noticeNum") int noticeNum);
}
