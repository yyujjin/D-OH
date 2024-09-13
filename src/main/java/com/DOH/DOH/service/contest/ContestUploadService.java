package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;

import java.util.List;

public interface ContestUploadService {

    public void saveContest(ContestUploadDTO contestUploadDTO);

    // ID로 콘테스트 조회
    public ContestUploadDTO findContestById(Long contestId);

    List<String> getContestTypes(); // 업종 목록을 가져오는 메서드 추가

    public String generateOrderNumber();
}
