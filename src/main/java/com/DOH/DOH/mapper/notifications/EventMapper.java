package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface EventMapper {

    /**
     * 정식 저장된 이벤트 목록을 가져오는 메서드
     * @return 정식 저장된 이벤트 목록
     */
    List<EventDTO> getEventList();

    /**
     * 임시 저장된 이벤트 목록을 가져오는 메서드
     * @return 임시 저장된 이벤트 목록
     */
    List<EventDTO> getTempEventList();

    /**
     * 임시 저장된 이벤트 전체 목록을 가져오는 메서드 (페이징 없이)
     * @return 임시 저장된 이벤트 전체 목록
     */
    List<EventDTO> findTempEvents();

    /**
     * 새로운 이벤트를 작성하는 메서드
     * @param event 이벤트 DTO 객체
     */
    void insertEvent(EventDTO event);

    /**
     * 이벤트 번호로 이벤트를 조회하는 메서드
     * @param eventNum 조회할 이벤트 번호
     * @return 조회된 이벤트 DTO
     */
    EventDTO selectEventById(@Param("eventNum") Long eventNum);

    /**
     * 이벤트 수정 메서드
     * @param event 수정된 이벤트 DTO 객체
     */
    void updateEvent(EventDTO event);

    /**
     * 이벤트 삭제 메서드
     * @param eventNum 삭제할 이벤트 번호
     */
    void deleteEvent(@Param("eventNum") Long eventNum);

    /**
     * 정식 저장된 이벤트의 총 개수를 가져오는 메서드
     * @return 정식 저장된 이벤트의 총 개수
     */
    int getTotalEvents();

    /**
     * 임시 저장된 이벤트의 총 개수를 가져오는 메서드
     * @return 임시 저장된 이벤트의 총 개수
     */
    int getTotalTempEvents();

    void updateTempEvent(EventDTO eventDTO);

    EventDTO selectEventById(Long eventNum, Model model); // 상세보기 메소드 추가
}
