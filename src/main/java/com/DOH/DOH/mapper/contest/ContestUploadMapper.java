package com.DOH.DOH.mapper.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContestUploadMapper {
    public void insertContest(ContestUploadDTO contestUploadDTO);

    // ID를 통해 콘테스트 정보 조회
    @Select("SELECT * FROM contest_Upload WHERE id = #{contestId}")
    ContestUploadDTO selectContestById(@Param("contestId") Long contestId);
}
