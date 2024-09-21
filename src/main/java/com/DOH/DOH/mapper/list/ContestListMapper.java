package com.DOH.DOH.mapper.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface ContestListMapper {
//    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize,  @Param("orderType") String orderType);
    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    public int getTotalCount(); // 전체 게시물 수를 조회하는 메서드
    public void saveContestApply(ApplyDTO dto);
    void hitUp(int contestId);

//    boolean searchScrap(@Param("userEmail") String uerEmail,@Param("conestId") int contestId);
    boolean searchScrap(@RequestParam HashMap<String, String> param);
//    void addScrap(@Param("userEmail") String uerEmail,@Param("conestId") int contestId);
    void addScrap(@RequestParam HashMap<String, String> param);
    void deleteScrap(@RequestParam HashMap<String, String> param);
}
