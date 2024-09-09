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

    // 이벤트 목록 조회
    @Override
    public void getEventList(int page, Model model) {
        int offset = (page - 1) * 10;
        List<EventDTO> eventList = eventMapper.getEventList(offset, 10);
        model.addAttribute("eventList", eventList);
    }

    // 이벤트 작성
    @Override
    public void writeEvent(EventDTO event) {
        eventMapper.writeEvent(event);
    }

    // 이벤트 임시 저장
    @Override
    public void saveEvent(EventDTO event) {
        event.setEventTempSave(true);
        eventMapper.writeEvent(event);
    }

    // 이벤트 삭제
    @Override
    public void deleteEvent(int eventNum) {
        eventMapper.deleteEvent(eventNum);
    }
}
