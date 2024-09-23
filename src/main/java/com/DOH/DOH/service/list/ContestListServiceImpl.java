package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.mapper.list.ContestListMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

@Slf4j
@Service("ContestListService")
public class ContestListServiceImpl implements ContestListService {

    @Autowired
    ContestListMapper contestListMapper;

    @Override
//    public ArrayList<ContestListDTO> getContestList(PagingDTO dto) {
    public ArrayList<ContestListDTO> getContestList(PagingDTO dto, String orderType) {
        int offset = (dto.getCurrentPage() - 1) * dto.getPageSize();  // 페이징 offset 계산
        int pageSize = dto.getPageSize();

        return contestListMapper.getContestList(offset, pageSize, orderType);
    }

    //컨테스트 저장
    public void saveContest(ApplyDTO applyDTO){
        contestListMapper.saveContest(applyDTO);

    public int getTotalCount(){// 전체 게시물 수를 조회하는 메서드
        return contestListMapper.getTotalCount();
    }

    public void hitUp(int contestId) {//조회수 증가 메서드
        contestListMapper.hitUp(contestId);
    }

    @Override
    public ArrayList<Integer> getScrapList(String userEmail){

        return contestListMapper.getScrapList(userEmail);
    }

    @Override
    public int scrap(String userEmail, int contestId) {
        int count = contestListMapper.searchScrap(userEmail, contestId);
        int result = 0;

        if(count == 0){
            contestListMapper.addScrap(userEmail, contestId);
        }else {
            contestListMapper.deleteScrap(userEmail,contestId);
            result = 1;
        }
        return result;
    }

    public void saveContestApply(ApplyDTO dto){
        contestListMapper.saveContestApply(dto);
    }
}
