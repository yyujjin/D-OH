package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;

import java.util.List;

public interface EventService {

    // 이벤트 목록 조회 (페이징 처리)
    List<EventDTO> getEventListLimited();

    List<EventDTO> getTempEventList();

    EventDTO getEventById(Long eventNum);  // 이벤트 번호로 이벤트를 조회하는 메서드

    // 이벤트 정식 저장
    void eventRegister(EventDTO eventDTO);

    // 이벤트 임시 저장 메서드
    void saveTempEvent(EventDTO eventDTO);

    // 이벤트 수정
    void eventUpdate(EventDTO eventDTO);

    // 이벤트 삭제
    void deleteEvent(Long eventNum);

    // 임시 저장 수정
    void updateTempEvent(EventDTO eventDTO);

}
