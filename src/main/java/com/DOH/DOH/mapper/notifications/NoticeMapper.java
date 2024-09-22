package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NoticeMapper {

    /**
     * 정식 저장된 공지사항 목록을 가져오는 메서드 (페이징 처리)
     * @param offset 데이터베이스에서 가져올 시작 위치
     * @param limit 페이지당 가져올 데이터 수
     * @return 정식 저장된 공지사항 목록
     */
    List<NoticeDTO> getNoticeList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 임시 저장된 공지사항 목록을 가져오는 메서드 (페이징 처리)
     * @param offset 데이터베이스에서 가져올 시작 위치
     * @param limit 페이지당 가져올 데이터 수
     * @return 임시 저장된 공지사항 목록
     */
    List<NoticeDTO> getTempNoticeList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 임시 저장된 공지사항 전체 목록을 가져오는 메서드 (페이징 없이)
     * @return 임시 저장된 공지사항 전체 목록
     */
    List<NoticeDTO> findTempNotices();

    /**
     * 새로운 공지사항을 작성하는 메서드
     * @param notice 공지사항 DTO 객체
     */
    void insertNotice(NoticeDTO notice);

    /**
     * 공지사항 번호로 공지사항을 조회하는 메서드
     * @param noticeNum 조회할 공지사항 번호
     * @return 조회된 공지사항 DTO
     */
    NoticeDTO selectNoticeById(@Param("noticeNum") Long noticeNum);

    /**
     * 공지사항 수정 메서드
     * @param notice 수정된 공지사항 DTO 객체
     */
    void updateNotice(NoticeDTO notice);

    /**
     * 공지사항 삭제 메서드
     * @param noticeNum 삭제할 공지사항 번호
     */
    void deleteNotice(@Param("noticeNum") Long noticeNum);

    /**
     * 정식 저장된 공지사항의 총 개수를 가져오는 메서드
     * @return 정식 저장된 공지사항의 총 개수
     */
    int getTotalNotices();

    /**
     * 임시 저장된 공지사항의 총 개수를 가져오는 메서드
     * @return 임시 저장된 공지사항의 총 개수
     */
    int getTotalTempNotices();
}
