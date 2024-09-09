package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.springframework.ui.Model;

public interface EventService {

    // 이벤트 목록 조회
    void getEventList(int page, Model model);

    // 이벤트 작성
    void writeEvent(EventDTO event);

    // 이벤트 임시 저장
    void saveEvent(EventDTO event);

    // 이벤트 삭제
    void deleteEvent(int eventNum);
}
