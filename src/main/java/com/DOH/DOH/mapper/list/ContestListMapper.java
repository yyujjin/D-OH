package com.DOH.DOH.mapper.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.dto.list.PagingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ContestListMapper {
//    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize,  @Param("orderType") String orderType);
    public ArrayList<ContestListDTO> getContestList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    public int getTotalCount(); // 전체 게시물 수를 조회하는 메서드
}
