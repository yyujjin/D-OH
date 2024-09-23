package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.mapper.notifications.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    /**
     * 이벤트 목록을 가져오는 메서드
     * @return 이벤트 목록
     */
    @Override
    public List<EventDTO> getEventListLimited() {
        List<EventDTO> eventList = eventMapper.getEventList(); // 모든 이벤트 목록 조회
        return (eventList != null) ? eventList : new ArrayList<>();
    }

    /**
     * 임시 저장된 이벤트 전체 목록을 가져오는 메서드
     * @return 임시 저장된 이벤트 목록
     */
    @Override
    public List<EventDTO> getTempEventList() {
        List<EventDTO> tempEvents = eventMapper.findTempEvents(); // 페이징 없이 전체 임시 저장 목록 조회
        return (tempEvents != null) ? tempEvents : new ArrayList<>();
    }

    /**
     * 새로운 이벤트를 작성하는 메서드
     * @param eventDTO 이벤트 DTO 객체 (제목, 내용, 작성자 등)
     */
    @Override
    public void eventRegister(EventDTO eventDTO) {
        // 이벤트 제목이 빈 값이 아닐 경우 설정
        if (eventDTO.getEventTitle() == null || eventDTO.getEventTitle().isEmpty()) {
            eventDTO.setEventTitle("제목 없음"); // 기본 제목 설정
        }

        eventMapper.insertEvent(eventDTO); // 이벤트 저장
    }

    /**
     * 이벤트 임시 저장 메서드
     * @param eventDTO 임시 저장할 이벤트 객체
     */
    @Override
    public void saveTempEvent(EventDTO eventDTO) {
        // 현재 임시 저장된 이벤트 수 확인
        List<EventDTO> tempEvents = eventMapper.findTempEvents();

        if (tempEvents.size() >= 3) {
            throw new IllegalStateException("임시 저장된 이벤트는 최대 3개까지만 저장할 수 있습니다.");
        }

        eventDTO.setEventTempSave(true); // 임시 저장으로 설정
        eventMapper.insertEvent(eventDTO);
    }

    /**
     * 기존 이벤트를 수정하는 메서드
     * @param eventDTO 수정된 이벤트 DTO 객체
     */
    @Override
    public void eventUpdate(EventDTO eventDTO) {
        eventMapper.updateEvent(eventDTO);
    }

    /**
     * 이벤트를 삭제하는 메서드
     * @param eventNum 삭제할 이벤트 번호
     */
    @Override
    public void deleteEvent(Long eventNum) {
        eventMapper.deleteEvent(eventNum);
    }

    @Override
    public void updateTempEvent(EventDTO eventDTO) {
        eventMapper.updateTempEvent(eventDTO);
    }

    @Override
    public EventDTO getEventById(Long eventNum, ModelMap modelMap) {
        return eventMapper.selectEventById(eventNum); // 메퍼에서 호출
    }
}
