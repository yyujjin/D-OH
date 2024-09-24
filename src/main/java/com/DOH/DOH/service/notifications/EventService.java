package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.springframework.ui.Model;

import java.util.List;

public interface EventService {

    // 이벤트 목록 조회
    List<EventDTO> getEventListLimited();

    List<EventDTO> getTempEventList();

    // 이벤트 정식 저장
    void eventRegister(EventDTO eventDTO, Model model);

    // 이벤트 임시 저장 메서드
    void saveTempEvent(EventDTO eventDTO);

    // 이벤트 수정
    void eventUpdate(EventDTO eventDTO,Model model);

    // 이벤트 삭제
    void deleteEvent(Long eventNum);

    // 임시 저장 수정
    void updateTempEvent(EventDTO eventDTO);

    //이벤트 상세 보기
    EventDTO getEventById(Long eventNum, Model model); // 상세보기 메소드 추가

}
