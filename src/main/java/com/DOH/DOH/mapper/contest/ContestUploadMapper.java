package com.DOH.DOH.mapper.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContestUploadMapper {
    public void insertContest(ContestUploadDTO contestUploadDTO);
}
