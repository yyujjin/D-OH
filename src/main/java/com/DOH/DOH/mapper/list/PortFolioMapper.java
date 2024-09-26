package com.DOH.DOH.mapper.list;

import com.DOH.DOH.dto.list.PortFolioDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface PortFolioMapper {
    ArrayList<PortFolioDTO> getPortFolioList(@Param("offset") int offset, @Param("pageSize") int pageSize);
    void hitUp(int id);//조회수 증가 메서드
    int getTotalCount();//전체 포토폴리오 조회 메서드
    PortFolioDTO getPortfolioInfo(int portfolioId);
}
