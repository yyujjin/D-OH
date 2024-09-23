package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;

import java.util.List;

public interface ContestUploadService {

    // 콘테스트 정보를 저장
    void saveContest(ContestUploadDTO contestUploadDTO);

    // 주문번호 업데이트
    void updateOrderNumber(ContestUploadDTO contestUploadDTO);

    // ID로 콘테스트 조회
    ContestUploadDTO findContestById(Long conNum);

    // 업종 목록을 가져오는 메서드
    List<String> getContestTypes();

    // 주문번호 생성
    String generateOrderNumber();

}
