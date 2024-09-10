package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.mapper.list.ContestListMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("ContestListService")
public class ContestListServiceImpl implements ContestListService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public ArrayList<ContestListDTO> getContestList() {
        ContestListMapper contestListMapper = sqlSession.getMapper(ContestListMapper.class);

        return contestListMapper.getContestList();
    }}
