package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.springframework.ui.Model;

public interface EventService {

    /**
     * 현재 페이지 번호를 기준으로 이벤트 목록을 가져오는 메서드
     * @param page 현재 페이지 번호
     * @return 이벤트 DTO 객체 리스트
     */
    void getEventList(int page, Model model);

    /**
     * 새로운 이벤트를 작성하는 메서드
     * @param eventDTO 이벤트 DTO 객체
     */
    void writeEvent(EventDTO eventDTO);

    /**
     * 기존 이벤트를 수정하는 메서드
     * @param eventDTO 수정된 이벤트 DTO 객체
     */
    void updateEvent(EventDTO eventDTO);

    /**
     * 이벤트를 삭제하는 메서드
     * @param eventNum 삭제할 이벤트 번호
     */
    void deleteEvent(int eventNum);

    /**
     * 이벤트 번호로 특정 이벤트를 조회하는 메서드
     * @param eventNum 조회할 이벤트 번호
     * @return 조회된 이벤트 DTO 객체
     */
    EventDTO getEventById(int eventNum);
}
