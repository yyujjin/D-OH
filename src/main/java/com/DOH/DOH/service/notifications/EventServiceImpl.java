package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.mapper.notifications.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    /**
     * 이벤트 목록을 가져와서 모델에 추가하는 메서드
     * @param page 현재 페이지 번호 (페이징 처리용)
     * @param model 이벤트 목록을 저장할 모델 객체
     */
    @Override
    public void getEventList(int page, Model model) {
        int offset = (page - 1) * 10; // 페이징 처리를 위한 offset 계산
        List<EventDTO> eventList = eventMapper.getEventList(offset); // 이벤트 목록 가져오기
        model.addAttribute("eventList", eventList); // 모델에 이벤트 목록 추가
    }

    /**
     * 새로운 이벤트를 작성하는 메서드
     * @param eventDTO 이벤트 DTO 객체 (제목, 내용, 이미지 등)
     */
    @Override
    public void writeEvent(EventDTO eventDTO) {
        eventMapper.writeEvent(eventDTO); // 이벤트 작성 로직 실행
    }

    /**
     * 기존 이벤트를 수정하는 메서드
     * @param eventDTO 수정된 이벤트 DTO 객체
     */
    @Override
    public void updateEvent(EventDTO eventDTO) {
        eventMapper.updateEvent(eventDTO); // 이벤트 수정 로직 실행
    }

    /**
     * 이벤트를 삭제하는 메서드
     * @param eventNum 삭제할 이벤트 번호
     */
    @Override
    public void deleteEvent(int eventNum) {
        eventMapper.deleteEvent(eventNum); // 이벤트 삭제 로직 실행
    }

    /**
     * 이벤트 번호로 특정 이벤트를 조회하는 메서드
     * @param eventNum 조회할 이벤트 번호
     * @return 조회된 이벤트 DTO 객체
     */
    @Override
    public EventDTO getEventById(int eventNum) {
        return eventMapper.getEventById(eventNum); // 특정 이벤트 조회
    }
}
