package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {

    // 이벤트 목록 조회 (페이징 처리)
    List<EventDTO> getEventList(@Param("offset") int offset, @Param("limit") int limit);

    // 이벤트 상세 조회
    EventDTO getEventDetail(@Param("eventNum") int eventNum);

    // 이벤트 작성
    void writeEvent(EventDTO event);

    // 이벤트 수정
    void updateEvent(EventDTO event);

    // 이벤트 삭제
    void deleteEvent(@Param("eventNum") int eventNum);
}
