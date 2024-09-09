package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.mapper.contest.ContestUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestUploadServiceImpl implements ContestUploadService {
    @Autowired
    private ContestUploadMapper contestMapper;

    @Override
    public void saveContest(ContestUploadDTO contestUploadDTO) {
        contestMapper.insertContest(contestUploadDTO);
    }

    @Override
    public ContestUploadDTO findContestById(Long contestId) {
        return contestMapper.selectContestById(contestId);
    }

    // 업종 목록을 가져오는 메서드 구현
    @Override
    public List<String> getContestTypes() {
        return contestMapper.findconTypes();
    }
}
