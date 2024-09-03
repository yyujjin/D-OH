package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.mapper.contest.ContestUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestUploadServiceImpl implements ContestUploadService {
    @Autowired
    private ContestUploadMapper contestMapper;

    @Override
    public void saveContest(ContestUploadDTO contestUploadDTO) {
        contestMapper.insertContest(contestUploadDTO);
    }
}
