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
import java.util.List;

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

    public int getTotalCount() {// 전체 게시물 수를 조회하는 메서드
        return contestListMapper.getTotalCount();
    }

    public void hitUp(int contestId) {//조회수 증가 메서드
        contestListMapper.hitUp(contestId);
    }

    @Override
    public ArrayList<Integer> getScrapList(String userEmail) {

        return contestListMapper.getScrapList(userEmail);
    }

    @Override
    public int scrap(String userEmail, int contestId) {
        int count = contestListMapper.searchScrap(userEmail, contestId);
        int result = 0;

        if (count == 0) {
            contestListMapper.addScrap(userEmail, contestId);
        } else {
            contestListMapper.deleteScrap(userEmail, contestId);
            result = 1;
        }
        return result;
    }

    @Override
    public void saveContest(ApplyDTO applyDTO) {
        contestListMapper.saveContest(applyDTO);
    }

    //유저이메일로 지원한 컨테스트 목록 가져오기
    @Override
    public List<ApplyDTO> getApplicationList(String userEmail) {
        return contestListMapper.getApplicationList(userEmail);
    }

    @Override
    public ContestListDTO contestInfo(Long id) {

        return contestListMapper.contestInfo(id);
    }

    @Override
    public int getApplyCount(Long id) {

        return contestListMapper.getApplyCount(id);

    }

    // conNum과 userEmail을 기반으로 작성된 글을 조회하는 메서드
    @Override
    public ApplyDTO getApplyByConNumAndUserEmail(Long conNum, String userEmail) {
        return contestListMapper.findApplyByConNumAndUserEmail(conNum, userEmail);
    }
}