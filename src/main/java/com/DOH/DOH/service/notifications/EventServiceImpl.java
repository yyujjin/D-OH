package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.mapper.notifications.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    /**
     * 이벤트 목록을 가져오는 메서드 (12개로 제한)
     * @return 이벤트 목록 (최대 12개)
     */
    @Override
    public List<EventDTO> getEventListLimited() {
        List<EventDTO> eventList = eventMapper.getEventList();
        if (eventList != null && eventList.size() > 12) {
            return eventList.subList(0, 12); // 12개로 제한
        }
        return (eventList != null) ? eventList : new ArrayList<>(); // null 체크 후 반환
    }

    /**
     * 임시 저장된 이벤트 전체 목록을 가져오는 메서드
     * @return 임시 저장된 이벤트 목록
     */
    @Override
    public List<EventDTO> getTempEventList() {
        List<EventDTO> tempEvents = eventMapper.getTempSavedEvents();
        return (tempEvents != null) ? tempEvents : new ArrayList<>(); // null 체크
    }


    /**
     * 새로운 이벤트를 작성하는 메서드
     * @param eventDTO 이벤트 DTO 객체 (제목, 내용, 작성자 등)
     */
    @Override
    public void eventRegister(EventDTO eventDTO, Model model) {
        // 이벤트 제목이 빈 값이 아닐 경우 설정
        if (eventDTO.getEventTitle() == null || eventDTO.getEventTitle().isEmpty()) {
            eventDTO.setEventTitle("제목 없음"); // 기본 제목 설정
        }

        eventMapper.insertEvent(eventDTO); // 이벤트 저장

        // 저장 후 모델에 이벤트 정보 추가
        model.addAttribute("event", eventDTO);
    }

    /**
     * 이벤트 임시 저장 메서드
     * @param eventDTO 임시 저장할 이벤트 객체
     */
    @Override
    public void saveTempEvent(EventDTO eventDTO) {
        // 현재 임시 저장된 이벤트 수 확인
        List<EventDTO> tempEvents = eventMapper.getTempSavedEvents();

        if (tempEvents != null && tempEvents.size() >= 3) {
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
    public void eventUpdate(EventDTO eventDTO, Model model) {
        eventMapper.updateEvent(eventDTO);

        // 업데이트 후 모델에 수정된 이벤트 정보 추가
        model.addAttribute("updatedEvent", eventDTO);
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
    public EventDTO getEventById(Long eventNum, Model model) {
        EventDTO event = eventMapper.selectEventById(eventNum);

        // 조회된 이벤트를 모델에 추가
        model.addAttribute("event", event);

        return event;
    }
}