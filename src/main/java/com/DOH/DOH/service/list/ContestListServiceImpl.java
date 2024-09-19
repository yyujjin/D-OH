package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ApplyDTO;
import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.mapper.list.ContestListMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service("ContestListService")
public class ContestListServiceImpl implements ContestListService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public ArrayList<ContestListDTO> getContestList(PagingDTO dto) {
        ContestListMapper contestListMapper = sqlSession.getMapper(ContestListMapper.class);
        int offset = (dto.getCurrentPage() - 1) * dto.getPageSize();  // 페이징 offset 계산
        int pageSize = dto.getPageSize();

        return contestListMapper.getContestList(offset, pageSize);
    }

    public int getTotalCount(){
        ContestListMapper contestListMapper = sqlSession.getMapper(ContestListMapper.class);

        return contestListMapper.getTotalCount();
    } // 전체 게시물 수를 조회하는 메서드

    public void saveContestApply(ApplyDTO dto){
        ContestListMapper contestListMapper = sqlSession.getMapper(ContestListMapper.class);
        contestListMapper.saveContestApply(dto);
    }
}
