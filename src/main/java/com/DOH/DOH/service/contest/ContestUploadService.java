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

    //컨테스트 지원자 목록 가져오기
    List<String>getContestApplicants(Long contestNum);

    //유저 이메일로 생성한 컨테스트 목록 조회하기
    List<ContestUploadDTO>getContestsByUserEmail(String userEmail);

    //컨테스트 모집 종료
    void finishContest(Long conNum);

    //모집 마감됐는지 isFinished 컬럼으로 확인하기
    int isFinishedContest(Long conNum);
}
