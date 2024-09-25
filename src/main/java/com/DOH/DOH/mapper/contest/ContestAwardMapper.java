package com.DOH.DOH.mapper.contest;

import com.DOH.DOH.dto.contest.AwardDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContestAwardMapper {
    //컨테스트 결과 저장
    void saveAwardResult(AwardDTO awardDTO);
}
