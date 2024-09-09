package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.mapper.notifications.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    private static final int PAGE_SIZE = 10;  // 페이지당 이벤트 수

    // 이벤트 목록 조회 (리스트 반환)
    @Override
    public List<EventDTO> getEventList(int page) {
        int offset = (page - 1) * PAGE_SIZE;  // 페이지에 따른 오프셋 계산
        return eventMapper.getEventList(offset, PAGE_SIZE);  // 이벤트 목록을 반환
    }

    // 전체 페이지 수 계산 (페이지네이션)
    @Override
    public int getTotalPages() {
        int totalEvents = eventMapper.getTotalEventCount();
        return (int) Math.ceil((double) totalEvents / PAGE_SIZE);  // 전체 페이지 수 계산
    }

    // 이벤트 상세 조회
    @Override
    public EventDTO getEventDetail(int eventNum) {
        return eventMapper.getEventDetail(eventNum);
    }

    // 이벤트 작성
    @Override
    public void writeEvent(EventDTO event) {
        eventMapper.writeEvent(event);
    }

    // 이벤트 임시 저장
    @Override
    public void saveTempEvent(EventDTO event) {
        event.setEventTempSave(true);  // 임시 저장 플래그 설정
        eventMapper.writeEvent(event);  // 이벤트 저장
    }

    // 이벤트 수정
    @Override
    public void updateEvent(EventDTO event) {
        eventMapper.updateEvent(event);
    }

    // 이벤트 삭제
    @Override
    public void deleteEvent(int eventNum) {
        eventMapper.deleteEvent(eventNum);
    }
}
