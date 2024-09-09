package com.DOH.DOH.mapper.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContestUploadMapper {
    // 콘테스트 정보를 DB에 삽입
    public void insertContest(ContestUploadDTO contestUploadDTO);

    // ID로 콘테스트 조회
    ContestUploadDTO selectContestById(@Param("conNum") Long conNum);

    // 업종 목록 조회 (어노테이션 기반)
    @Select("SELECT DISTINCT conType FROM contest_contype")
    List<String> findconTypes();  // 업종 목록을 가져오는 메서드
}
