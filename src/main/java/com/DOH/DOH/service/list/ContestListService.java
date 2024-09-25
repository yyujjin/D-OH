package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public interface ContestListService {
    //    public ArrayList<ContestListDTO> getContestList(int page, int pageSize, String orderType);
    public ArrayList<ContestListDTO> getContestList(PagingDTO dto, String orderType);

    public int getTotalCount(); // 전체 게시물 수를 조회하는 메서드
    public void hitUp(int contestId);//조회수 증가 메서드
    ArrayList<Integer> getScrapList(String userEmail);
    int scrap(String email, int contestId);
    void saveContest(ApplyDTO applyDTO);

    ContestListDTO contestInfo(Long id);
    int getApplyCount(Long id);
}