package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;

public interface ContestUploadService {

    public void saveContest(ContestUploadDTO contestUploadDTO);

    // ID로 콘테스트 조회
    public ContestUploadDTO findContestById(Long contestId);
}
