package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;

import java.util.List;

public interface EventService {

    // 이벤트 목록 조회 (리스트 반환)
    List<EventDTO> getEventList(int page);

    // 전체 페이지 수 계산 (페이지네이션)
    int getTotalPages();

    // 이벤트 상세 조회
    EventDTO getEventDetail(int eventNum);

    // 이벤트 작성
    void writeEvent(EventDTO event);

    // 이벤트 임시 저장
    void saveTempEvent(EventDTO event);

    // 이벤트 수정
    void updateEvent(EventDTO event);

    // 이벤트 삭제
    void deleteEvent(int eventNum);
}
