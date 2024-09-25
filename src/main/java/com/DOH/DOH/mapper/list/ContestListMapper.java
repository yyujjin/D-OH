package com.DOH.DOH.mapper.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface ContestListMapper {
    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize,  @Param("orderType") String orderType);
//    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    public int getTotalCount(); // 전체 게시물 수를 조회하는 메서드

    //컨테스트 저장
    void saveContest(ApplyDTO applyDTO);

    void hitUp(int contestId);
    ArrayList<Integer> getScrapList(String userEmail);
    int searchScrap(@Param("userEmail") String userEmail,@Param("contestId") int contestId);
    void addScrap(@Param("userEmail") String uerEmail,@Param("contestId") int contestId);
    void deleteScrap(@Param("userEmail") String uerEmail,@Param("contestId") int contestId);
    List<ApplyDTO> getApplicationList(String userEmail);
}
